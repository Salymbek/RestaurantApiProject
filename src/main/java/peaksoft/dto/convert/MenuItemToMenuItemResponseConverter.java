package peaksoft.dto.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.model.MenuItem;

@Component
public class MenuItemToMenuItemResponseConverter implements Converter<MenuItem, MenuItemResponse> {
    @Override
    public MenuItemResponse convert(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.getIsVegetarian()
        );
    }
}