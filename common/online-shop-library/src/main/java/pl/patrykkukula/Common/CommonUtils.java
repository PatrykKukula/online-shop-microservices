package pl.patrykkukula.Common;

import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class CommonUtils {

    public static Sort setSort(String sortDirection, String sortProperty){
        Sort sort = Sort.by(sortProperty);
        if (sortDirection.equalsIgnoreCase("DESC")){
            return sort.descending();
        }
        return sort.ascending();
    }
    public static URI setLocation(Integer id, String path){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{" + path + "}")
                .buildAndExpand(id)
                .toUri();
    }
}
