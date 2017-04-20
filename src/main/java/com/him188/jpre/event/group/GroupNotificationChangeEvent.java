package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 群公告变更
 *
 * @author Him188
 */
public class GroupNotificationChangeEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String newNotification;

	public GroupNotificationChangeEvent(RobotQQ robot, Group group, QQ qq, String newNotification) {
		super(robot, group, qq);
		this.newNotification = newNotification;
	}

	public String getNewNotification() {
		return newNotification;
	}
}
