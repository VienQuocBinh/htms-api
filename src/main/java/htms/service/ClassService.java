package htms.service;

import htms.api.request.ClassRequest;
import htms.api.response.ClassResponse;

import java.util.List;

public interface ClassService {
    ClassResponse createClass(ClassRequest request);

    List<ClassResponse> getClasses();
}
