package com.xsl.search.export;

import java.io.IOException;
import java.util.List;

public interface WordCheckService {
    List<String> check(String sentence) throws IOException;
}
