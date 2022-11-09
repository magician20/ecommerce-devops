package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);

        Item item1 = getItem(1L, "item-1");
        Item item2 = getItem(2L, "item-3");
        Item item3 = getItem(3L, "item-3");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        when(itemRepository.findByName(item2.getName())).thenReturn(Arrays.asList(item2, item3));
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2, item3));
    }

    @Test
    public void testFindAllItems() {
        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        int getResponseCode = responseEntity.getStatusCodeValue();
        List<Item> items = responseEntity.getBody();

        assertEquals(getResponseCode, HttpStatus.OK.value());
        assertNotNull(items);
        assertEquals(items.size(), 3);
    }

    @Test
    public void testFindItemsByID () {
        ResponseEntity<Item> responseEntity = itemController.getItemById(1L);
        int getResponseCode = responseEntity.getStatusCodeValue();
        Item item = responseEntity.getBody();

        assertEquals(getResponseCode, HttpStatus.OK.value());
        assertNotNull(item);
        assertEquals(item.getId().longValue(), 1L);
    }

    @Test
    public void testWhenItemDoesNotExist () {
        ResponseEntity<Item> responseEntity = itemController.getItemById(4L);
        int getResponseCode = responseEntity.getStatusCodeValue();

        assertEquals(getResponseCode, HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testFindItemByName () {
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item-3");
        int getResponseCode = responseEntity.getStatusCodeValue();
        List<Item> items = responseEntity.getBody();

        assertEquals(getResponseCode, HttpStatus.OK.value());
        assertNotNull(items);
        assertEquals(items.size(), 2);
    }

    @Test
    public void testFindItemByNameWhenItemDoesnotExist () {
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("");
        int getResponseCode = responseEntity.getStatusCodeValue();

        assertEquals(getResponseCode, HttpStatus.NOT_FOUND.value());
    }

    private static Item getItem(long id, String name) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        return item;
    }
}
