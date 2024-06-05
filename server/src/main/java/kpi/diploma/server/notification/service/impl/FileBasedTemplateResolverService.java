package kpi.diploma.server.notification.service.impl;

import kpi.diploma.server.notification.service.TemplateResolverService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

@Service
public class FileBasedTemplateResolverService implements TemplateResolverService {

    private static final String FILE_PATH_TEMPLATE = "templates/%s.html";

    @SneakyThrows
    @Override
    public String readContent(String templateName) {
        var resource = new ClassPathResource(format(FILE_PATH_TEMPLATE, templateName));
        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
