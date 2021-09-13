package com.company.controller;

import com.company.model.Phonebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhonebookManagement {
    private List<Phonebook> phonebooks = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public List<Phonebook> getPhonebooks() {
        return phonebooks;
    }

    public void setPhonebooks(List<Phonebook> phonebooks) {
        this.phonebooks = phonebooks;
    }

    public void addNew(Phonebook phonebook) {
        phonebooks.add(phonebook);
    }

    public void remove(Phonebook phonebook) {
        phonebooks.remove(phonebook);
    }

    public Phonebook removePhonebook(int index) {
        return phonebooks.remove(index);
    }

    public Phonebook update(int index, Phonebook phonebook) {
        return phonebooks.set(index, phonebook);
    }

    public void displayAll() {
        if (phonebooks.isEmpty()) {
            System.out.println("Không thấy số di động nào!!");
        }
        int count = 0;
        for (Phonebook phonebook: phonebooks) {
            System.out.println(phonebook);
            count++;
            if (count == 5) {
                count = 0;
                System.out.println("Nhấn Enter để hiển thị danh bạ tiếp theo");
                scanner.nextLine();
            }
        }
    }

    public int findByPhoneNumber(String phoneNumber) {
        int index = -1;
        for (int i = 0; i < phonebooks.size(); i++) {
            if (phonebooks.get(i).getPhoneNumber().equals(phoneNumber)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public List<Phonebook> searchByName(String name) {
        List<Phonebook> searchList = new ArrayList<>();
        for (Phonebook phonebook: phonebooks) {
            if (phonebook.getName().contains(name)) {
                searchList.add(phonebook);
            }
        }
        return searchList;
    }

    public List<Phonebook> searchByPhone(String phoneNumber) {
        List<Phonebook> searchList = new ArrayList<>();
        for (Phonebook phonebook: phonebooks) {
            if (phonebook.getPhoneNumber().contains(phoneNumber)) {
                searchList.add(phonebook);
            }
        }
        return searchList;
    }
 }
