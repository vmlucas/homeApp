package com.home.mongocloud.repositories;

import com.home.mongocloud.models.StreamingInfo;

public interface IMDBRepository {

    StreamingInfo getStreamingInfo(String imdbId);
}
