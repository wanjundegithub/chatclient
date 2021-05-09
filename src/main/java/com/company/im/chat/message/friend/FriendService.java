package com.company.im.chat.message.friend;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.common.StateHelper;
import com.company.im.chat.context.SpringContext;
import com.company.im.chat.event.DoubleClickEvent;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.InitService;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.friend.res.ResFriendListPacket;
import com.company.im.chat.message.friend.res.ResFriendLoginPacket;
import com.company.im.chat.message.friend.res.ResFriendLogoutPacket;
import com.company.im.chat.ui.StageController;
import com.company.im.chat.ui.UiBaseService;
import com.company.im.chat.ui.View;
import com.company.im.chat.util.ImageUtil;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/*
**
 */
@Service
public class FriendService implements InitService {

    private static final Logger logger= LoggerFactory.getLogger(FriendService.class);

    private Map<String, FriendItemBean> friends = new HashMap<>();

    @PostConstruct
    public void init(){
        MessageRouter.Instance.registerHandle(PacketType.ResFriendLogin,this::RespondFriendLogin);
        MessageRouter.Instance.registerHandle(PacketType.ResFriendLogout,this::RespondFriendLogout);
        MessageRouter.Instance.registerHandle(PacketType.ResFriendList,this::receiveFriendsList);
    }

    /*
    **好友登录应答（来自服务器端）
     */
    public void RespondFriendLogin(AbstractPacket packet){
        if(packet==null){
            logger.error("Friend Login message from server is null");
            return;
        }
        var resFriendLoginPacket=(ResFriendLoginPacket) packet;
        String friendName=resFriendLoginPacket.getFriendName();
        //界面处理
    }

    /*
    **好友登出应答（来自服务器端）
     */
    public void RespondFriendLogout(AbstractPacket packet){
        if(packet==null){
            logger.error("Friend Logout message from server is null");
            return;
        }
        var resFriendLogoutPacket=(ResFriendLogoutPacket)packet;
        String friendName= resFriendLogoutPacket.getFriendName();
        //界面处理
    }

    /**
     * 好友登录刷新
     *
     * @param friendName
     */
    public void onFriendLogin(String friendName) {
        FriendItemBean friend = friends.get(friendName);
        if (friend != null) {
            friend.setIsOnline(StateHelper.OnLine);
            List<FriendItemBean> friendItems = new ArrayList<>(friends.values());
            receiveFriendsList(friendItems);
        }
    }

    /**
     * 好友下线刷新
     *
     * @param friendName
     */
    public void onFriendLogout(String friendName) {
        FriendItemBean friend = friends.get(friendName);
        if (friend != null) {
            friend.setIsOnline(StateHelper.OffLine);
            List<FriendItemBean> friendItems = new ArrayList<>(friends.values());
            receiveFriendsList(friendItems);
        }
    }

    /**
     * 从接收的消息中解析出好友列表信息
     * @param packet
     */
    public void receiveFriendsList(AbstractPacket packet) {
        if(packet==null){
            logger.error("friends message is null");
            return;
        }
        ResFriendListPacket resFriends = (ResFriendListPacket) packet;
        UiBaseService.INSTANCE.runTaskInFxThread(() -> {
            receiveFriendsList(resFriends.getFriends());
        });
    }

    /**
     * 获取好友列表
     * @param friendItems
     */
    private void receiveFriendsList(List<FriendItemBean> friendItems) {
        friends.clear();
        for (FriendItemBean item : friendItems) {
            friends.put(item.getFriend().getUserName(), item);
        }

        UiBaseService.INSTANCE.runTaskInFxThread(() -> {
            refreshFriendsView(friendItems);
        });

    }

    /**
     * 根据好友名查询好友信息
     * @param friendName
     * @return
     */
    public FriendItemBean queryFriend(String friendName) {
        return this.friends.get(friendName);
    }

    /**
     * 界面刷新好友列表
     * @param friendItems
     */
    public void refreshFriendsView(List<FriendItemBean> friendItems) {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        //Main.fxml
        Stage stage = stageController.getStageBy(View.id.MainView);
        ScrollPane scrollPane = (ScrollPane) stage.getScene().getRoot().lookup("#friendSp");
        Accordion friendGroup = (Accordion) scrollPane.getContent();
        friendGroup.getPanes().clear();
        decorateFriendGroup(friendGroup,StateHelper.GroupName,friendItems);
    }

    /**
     * 装饰好友分组(只有一个分组)
     * @param container
     * @param groupName
     * @param friendItems
     */
    private void decorateFriendGroup(Accordion container, String groupName, List<FriendItemBean> friendItems) {
        ListView<Node> listView = new ListView<Node>();
        int onlineCount = 0;
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        for (FriendItemBean item : friendItems) {
            if (item.isFriendOnline()) {
                onlineCount++;
            }
            Pane pane = stageController.load(View.layout.FriendItem, Pane.class);
            decorateFriendItem(pane, item);
            listView.getItems().add(pane);
        }

        bindDoubleClickEvent(listView);
        String groupInfo = groupName + " " + onlineCount + "/" + friendItems.size();
        TitledPane tp = new TitledPane(groupInfo, listView);
        container.getPanes().add(tp);
    }

    /**
     * 装饰单个好友项
     * @param itemUi
     * @param friendItemBean
     */
    private void decorateFriendItem(Pane itemUi, FriendItemBean friendItemBean) {
        Label autographLabel = (Label) itemUi.lookup("#signature");
        autographLabel.setText(friendItemBean.getFriend().getSignature());
        Hyperlink usernameUi = (Hyperlink) itemUi.lookup("#userName");
        usernameUi.setText(friendItemBean.getFriendFullName());

        //隐藏域，聊天界面用
        Label userIdUi = (Label) itemUi.lookup("#userName");
        userIdUi.setText(String.valueOf(friendItemBean.getFriend().getUserName()));

        ImageView headImage = (ImageView) itemUi.lookup("#headIcon");

        if (!friendItemBean.isFriendOnline()) {
            headImage.setImage(ImageUtil.convertToGray(headImage.getImage()));
        }

    }

    /**
     * 双击好友项进行聊天
     * @param listView
     */
    private void bindDoubleClickEvent(ListView<Node> listView) {
        listView.setOnMouseClicked(new DoubleClickEvent<Event>() {
            @Override
            public void handle(Event event) {
                if (this.checkValid()) {
                    ListView<Node> view = (ListView<Node>) event.getSource();
                    Node selectedItem = view.getSelectionModel().getSelectedItem();
                    if (selectedItem == null)
                        return;
                    Pane pane = (Pane) selectedItem;
                    Label userNameUi = (Label) pane.lookup("#userName");

                    String friendName = userNameUi.getText();
                    FriendItemBean targetFriend = friends.get(friendName);
                    String selfUserName= SpringContext.getUserService().getUser().getUserName();
                    //不能跟自己聊天
                    if (friendName.equals(selfUserName)) {
                        return;
                    }
                    if (targetFriend != null) {
                        openChatToFriendPanel(targetFriend);
                    }
                }
            }
        });
    }

    /**
     * 打开聊天界面进行聊天
     * @param targetFriend
     */
    private void openChatToFriendPanel(FriendItemBean targetFriend) {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage chatStage = stageController.setStage(View.id.ChatToFriend);
        Hyperlink userNameUi = (Hyperlink) chatStage.getScene().getRoot().lookup("#userName");
        Label signatureUi = (Label) chatStage.getScene().getRoot().lookup("#signature");
        userNameUi.setText(targetFriend.getFriendFullName());
        signatureUi.setText(targetFriend.getFriendFullName());
    }



}
