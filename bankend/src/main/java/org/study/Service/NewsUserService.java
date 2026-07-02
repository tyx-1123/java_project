package org.study.Service;

import org.study.entity.result;
import org.study.entity.NewsUser;

public interface NewsUserService {

    public result login(NewsUser User);

    public result getUser(String token);

    public result register(NewsUser newUser);
}
