package htms.service;

import htms.api.request.ActivityRequest;
import htms.api.response.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface ActivityService {

    ActivityResponse saveNewActivity(ActivityRequest request);

}
