package org.rsr;

import org.rsr.serializer.Serializer;

public interface RouteSettings {

	public RouteSettings setMediaType(String mediatype);

	public RouteSettings setSerializer(Serializer serializer);
}
