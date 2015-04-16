package lambdacloud.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import lambdacloud.core.CloudVar;

/**
 */
public class CloudVarEncoder extends MessageToByteEncoder<CloudVar> {

	@Override
	protected void encode(ChannelHandlerContext ctx, CloudVar var, ByteBuf out) {
		// Convert to a BigInteger first for easier implementation.
		int nameLen = 0;
		int dataLen = var.getAll().length * 8;
		int packageLen = 0;
		byte[] allData = null;
		try {
			byte[] nameBytes = var.getLabel().getBytes("UTF-8");
			nameLen = nameBytes.length;
			packageLen = nameLen + dataLen;
			allData = new byte[packageLen];
			System.arraycopy(nameBytes, 0, allData, 0, nameLen);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] data = new byte[dataLen];
		ByteBuffer buf = ByteBuffer.wrap(data);
		for (double d : var.getAll()) {
			buf.putDouble(d);
		}
		System.arraycopy(buf.array(), 0, allData, nameLen, dataLen);

		// Write a message.
		out.writeByte((byte) 'V'); // magic number
		out.writeInt(nameLen); // name length
		out.writeInt(var.isOnCloud()?1:0);
		out.writeInt(dataLen); // data length
		out.writeBytes(allData); // data
	}
}