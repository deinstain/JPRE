package com.him188.jpre;

import com.him188.jpre.binary.Pack;
import com.him188.jpre.network.ConnectedClient;
import com.him188.jpre.network.NetworkPacketHandler;
import com.him188.jpre.network.packet.PacketIds;

import java.util.List;
import java.util.Vector;

/**
 * 酷 Q通讯类
 *
 * @see BaseCoolQCaller
 */
public final class CoolQCaller {
	public static final int RESULT_TYPE_ACCEPT = 1;
	public static final int RESULT_TYPE_DENIED = 2;
	public static final int REQUEST_TYPE_ACTIVE_JOIN = 1; //主动加入
	public static final int REQUEST_TYPE_INVITE = 2; //被邀请

	private static List<Object> results = new Vector<>();

	private static int waitForIntResult(){
		return Integer.parseInt(waitForStringResult());
	}

	private static long waitForLongResult(){
		return Long.parseLong(waitForStringResult());
	}

	@SuppressWarnings("StatementWithEmptyBody")
	private static String waitForStringResult() {
		synchronized (CoolQCaller.class) {//使正在等待返回值时, 指令不传达
			while (results.isEmpty()) ;
			return String.valueOf(results.remove(0));
		}
	}

	private static void runCommand(CommandId id, Object... args){
		synchronized (CoolQCaller.class) {
			for (ConnectedClient connectedClient : NetworkPacketHandler.getClients()) {
				connectedClient.getLastCtx().writeAndFlush(new Pack()
						.putByte(PacketIds.COMMAND)
						.putByte(id.getId())
						.putRaw(args)
						.getData()
				);
			}
		}
	}

	public static void addResult(Object result){
		results.add(result);
	}

	public static int CQ_sendPrivateMsg(int authCode, long QQ, String message) {
		runCommand(CommandId.SEND_PRIVATE_MESSAGE, true, authCode, QQ, message);
		return waitForIntResult();
	}

	public static int CQ_sendGroupMsg(int authCode, long group, String message) {
		runCommand(CommandId.SEND_GROUP_MESSAGE, true, authCode, group, message);
		return waitForIntResult();
	}

	public static int CQ_sendDiscussMsg(int authCode, long discuss, String message) {
		runCommand(CommandId.SEND_DISCUSS_MESSAGE, true, authCode, discuss, message, int.class);
		return waitForIntResult();
	}

	public static int CQ_sendLike(int authCode, long QQ) {
		runCommand(CommandId.SEND_LIKE, true, authCode, QQ);
		return waitForIntResult();
	}

	//新版QQ的10次赞
	public static int CQ_sendLikeV2(int authCode, long QQ, int times) {
		runCommand(CommandId.SEND_LIKE_V2, true, authCode, QQ, times);
		return waitForIntResult();
	}

	public static int CQ_getCookies(int authCode) {
		runCommand(CommandId.GET_COOKIES, true, authCode);
		return waitForIntResult();
	}

	/**
	 * 接受语音
	 *
	 * @param authCode  authCode
	 * @param file      接受消息的文件名
	 * @param outFormat 应用所需格式
	 *
	 * @return ? 似乎会直接返回 file
	 */
	public static String CQ_getRecord(int authCode, String file, String outFormat) {
		runCommand(CommandId.GET_RECORD, true, authCode, file, outFormat);
		return waitForStringResult();
	}

	public static int CQ_getCsrfToken(int authCode) {
		runCommand(CommandId.GET_CSRF_TOKEN, true, authCode);
		return waitForIntResult();
	}

	public static String CQ_getAppDirectory(int authCode) {
		runCommand(CommandId.GET_APP_DIRECTORY, true, authCode);
		return waitForStringResult();
	}

	public static long CQ_getLoginQQ(int authCode) {
		runCommand(CommandId.GET_APP_DIRECTORY, true, authCode);
		return waitForLongResult();
	}

	public static String CQ_getLoginNick(int authCode) {
		runCommand(CommandId.GET_LOGIN_NICK, true, authCode);
		return waitForStringResult();
	}

	public static int CQ_setGroupKick(int authCode, long group, long QQ, boolean block) {
		runCommand(CommandId.SET_GROUP_KICK, false, authCode, group, QQ, block);
		return waitForIntResult();
	}

	//time: s. 解禁0
	public static int CQ_setGroupBan(int authCode, long group, long QQ, long time) {
		runCommand(CommandId.SET_GROUP_BAN, false, authCode, group, QQ, time);
		return waitForIntResult();
	}

	public static int CQ_setGroupAdmin(int authCode, long group, long QQ, boolean admin) {
		runCommand(CommandId.SET_GROUP_ADMIN, false, authCode, group, QQ, admin);
		return waitForIntResult();
	}

	public static int CQ_setGroupSpecialTitle(int authCode, long group, long QQ, String title, long timeLimit) {
		runCommand(CommandId.SET_GROUP_SPECIAL_TITLE, false, authCode, group, QQ, title, timeLimit);
		return waitForIntResult();
	}

	public static int CQ_setGroupWholeBan(int authCode, long group, boolean ban) {
		runCommand(CommandId.SET_GROUP_WHOLE_BAN, false, authCode, group, ban);
		return waitForIntResult();
	}

	public static int CQ_setGroupAnonymousBan(int authCode, long group, String anonymousId, long time) {
		runCommand(CommandId.SET_GROUP_ANONYMOUS_BAN, false, authCode, group, anonymousId, time);
		return waitForIntResult();
	}

	public static int CQ_setGroupAnonymous(int authCode, long group, boolean enabled) {
		runCommand(CommandId.SET_GROUP_ANONYMOUS, false, authCode, group, enabled);
		return waitForIntResult();
	}

	public static int CQ_setGroupCard(int authCode, long group, long QQ, String newCard) {
		runCommand(CommandId.SET_GROUP_CARD, false, authCode, group, QQ, newCard);
		return waitForIntResult();
	}

	public static int CQ_setGroupLeave(int authCode, long group, boolean dissolve) {
		runCommand(CommandId.SET_GROUP_LEAVE, false, authCode, group, dissolve);
		return waitForIntResult();
	}

	public static int CQ_setDiscussLeave(int authCode, long discuss) {
		runCommand(CommandId.SET_DISCUSS_LEAVE, false, authCode, discuss);
		return waitForIntResult();
	}

	public static int CQ_setFriendAddRequest(int authCode, String requestId, int resultType, String nick) {
		runCommand(CommandId.SET_FRIEND_ADD_REQUEST, false, authCode, requestId, resultType, nick);
		return waitForIntResult();
	}


	@Deprecated
	public static int CQ_setGroupAddRequest(int authCode, String requestId, int requestType, int resultType) {
		runCommand(CommandId.SET_GROUP_ADD_REQUEST, false, authCode, requestId, requestType, resultType);
		return waitForIntResult();
	}

	public static int CQ_setGroupAddRequestV2(int authCode, String requestId, int requestType, int resultType, String reason) {
		runCommand(CommandId.SET_GROUP_ADD_REQUEST_V2, false, authCode, requestId, requestType, resultType, reason);
		return waitForIntResult();
	}

	public static int CQ_addLog(int authCode, int priority, String type, String message) {
		runCommand(CommandId.ADD_LOG, false, authCode, priority, type, message);
		return waitForIntResult();
	}

	public static int CQ_setFatal(int authCode, String message) {
		runCommand(CommandId.SET_FATAL, false, authCode, message);
		return waitForIntResult();
	}


	@Deprecated
	public static String CQ_getGroupMemberInfo(int authCode, long group, long QQ) {
		runCommand(CommandId.GET_GROUP_MEMBER_INFO, false, authCode, group, QQ);
		return waitForStringResult();
	}

	public static String CQ_getGroupMemberInfoV2(int authCode, long group, long QQ, boolean noCache) {
		runCommand(CommandId.GET_GROUP_MEMBER_INFO_V2, false, authCode, group, QQ, noCache);
		return waitForStringResult();
	}

	public static String CQ_getStrangerInfo(int authCode, long QQ, boolean noCache) {
		runCommand(CommandId.GET_STRANGER_INFO, false, authCode, QQ, noCache);
		return waitForStringResult();
	}

	// TODO: 2017/1/26 0026 RtlMoveMemory
	// TODO: 2017/1/26 0026 GlobalSize
}
