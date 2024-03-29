package server.net;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import server.util.ISAACRandomGen;

/**
 * Provides access to the encoders and decoders for the 508 protocol.
 * @author Graham
 *
 */
public class GameCodecFactory implements ProtocolCodecFactory {
	
	/**
	 * The encoder.
	 */
	private ProtocolEncoder encoder = new RS2ProtocolEncoder();
	
	/**
	 * The decoder.
	 */
	private ProtocolDecoder decoder;
	
	public GameCodecFactory(ISAACRandomGen inC) {
		decoder = new RS2ProtocolDecoder(inC);
	}

	@Override
	/**
	 * Get the encoder.
	 */
	public ProtocolEncoder getEncoder() throws Exception {
		return encoder;
	}

	@Override
	/**
	 * Get the decoder.
	 */
	public ProtocolDecoder getDecoder() throws Exception {
		return decoder;
	}

}
