package com.home.mongocloud.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.home.mongocloud.models.StreamingInfo;

@Component
public class SeriesIMDBRepository implements IMDBRepository {

  private RestTemplate restTemplate;
  private HttpEntity<Void> requestEntity;

  public SeriesIMDBRepository(){
    HttpHeaders headers = new HttpHeaders();
    headers.set("x-rapidapi-key", "6147f8766cmsh1e48e6e7bc39f7cp1071d5jsn509f99bcffdc");
    headers.set("x-rapidapi-host", "streaming-availability.p.rapidapi.com");
    this.requestEntity = new HttpEntity<>(headers); 
    restTemplate = new RestTemplate();
  }

  @Override
  @Cacheable("streamingInfos")
  public StreamingInfo getStreamingInfo(String imdbId) {
      try{
          ResponseEntity<StreamingInfo> responseEntity = restTemplate.exchange(
                  "https://streaming-availability.p.rapidapi.com/shows/"+imdbId, 
                  HttpMethod.GET, 
                  requestEntity, 
                  new ParameterizedTypeReference<StreamingInfo>() {
             });
          StreamingInfo info = responseEntity.getBody();  
          return info;    
      }
      catch (final HttpClientErrorException e) {
          StreamingInfo info = new StreamingInfo();
          info.setOverview(e.getStatusCode()+" "+e.getResponseBodyAsString()); 
          info.setCast(new String[0]);
          info.setRating(0);
          return info;
      }       
      
  }
}  