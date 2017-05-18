package net.mamoe.jpre.network.packet;

import net.mamoe.jpre.CommandId;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ServerStaticCommandPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_STATIC_COMMAND;

	private final Object[] args;
	private final CommandId id;

	public Object[] getArgs() {
		return args;
	}

	public CommandId getId() {
		return id;
	}

	public ServerStaticCommandPacket(CommandId commandId, Object[] args) {
		this.args = args;
		id = commandId;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();

		putByte(id.getId());
		putRaw(args);
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return 0;
	}
}