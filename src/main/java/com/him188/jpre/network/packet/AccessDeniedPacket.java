package com.him188.jpre.network.packet;

import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class AccessDeniedPacket extends Packet {
	public static final byte NETWORK_ID = PacketIds.ACCESS_DENIED;

	@Override
	public byte[] encode() {
		return new byte[0];
	}

	@Override
	public void decode(Unpack unpack) {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
