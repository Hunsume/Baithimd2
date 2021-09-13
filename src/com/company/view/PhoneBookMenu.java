package com.company.view;

import com.company.controller.PhonebookManagement;
import com.company.model.Phonebook;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class PhoneBookMenu {
    Scanner scanner = new Scanner(System.in);
    PhonebookManagement phonebookManagement = new PhonebookManagement();

    public void run() {
        int choice;
        do {
            menu();
            System.out.println("Nhập lựa chon của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    displayPhoneBook();
                    break;
                }
                case 2: {
                    addPhoneBook();
                    break;
                }
                case 3: {
                    editPhonebook();
                    break;
                }
                case 4: {
                    removePhoneBook();
                    break;
                }
                case 5: {
                    searhPhoneBook();
                    break;
                }
                case 6: {
                    readDataToFile();
                    break;
                }
                case 7: {
                    writeDataToFile();
                    break;
                }

            }
        } while (choice != 8);
    }

    public void displayPhoneBook() {
        System.out.println("--- Danh sách danh bạ ---");
        phonebookManagement.displayAll();

    }

    public void addPhoneBook() {
        System.out.println("Tạo danh bạ mới");
        Phonebook phonebook = creatPhoneBook();
        phonebookManagement.addNew(phonebook);
        System.out.println("Thêm danh bạ thành công!!!");
    }

    public void editPhonebook() {
        System.out.println("Nhập số điện thoại cần chỉnh sửa!!");
        String id = scanner.nextLine();
        int index = phonebookManagement.findByPhoneNumber(id);
        if (index != -1) {
            Phonebook phonebook = creatPhoneBook();
            scanner.nextLine();
            phonebookManagement.update(index, phonebook);
            System.out.println("Sửa đổi thành công!!");
        } else {
            System.out.println(" Không tìm thấy số điện thoại này!!");
        }
    }

    public void removePhoneBook() {
        System.out.println("Nhập số điện thoại muốn xóa ");
        String id = scanner.nextLine();
        int index = phonebookManagement.findByPhoneNumber(id);
        if (index != -1) {
            System.out.println("Bấm Y để xóa!!");
            String check = scanner.nextLine();
            if (check.equals("Y")) {
                phonebookManagement.removePhonebook(index);
                System.out.println("Xóa sản phẩm thành công");
            } else {
                if (id.equals(" ")) {
                    System.out.println("Quay lại menu");
                    return;
                }
            }
        }
        System.out.println(" Không tìm thấy số điện thoại nào!!!");
    }

    public void searhPhoneBook() {
        int choice;
        do {
            menuSearch();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    searchName();
                    break;
                }
                case 2: {
                    searchPhoneNumber();
                    break;
                }
            }
        } while (choice != 0);
    }

    public void searchName() {
        System.out.println("Nhập tên");
        String name = scanner.nextLine();
        List<Phonebook> phonebookList = phonebookManagement.searchByName(name);
        if (phonebookList.size() > 0) {
            for (Phonebook phonebook: phonebookList) {
                System.out.println(phonebook.toString());
            }
        } else {
            System.out.println("Không tìm thấy tên nào trung với danh bạ trong hệ thống!!");
        }
    }

    public void searchPhoneNumber() {
        System.out.println("Nhập số điện thoại");
        String phone = inputPhoneNumber();
        List<Phonebook> phonebookList = phonebookManagement.searchByPhone(phone);
        if (phonebookList.size() > 0) {
            for (Phonebook phonebook: phonebookList) {
                System.out.println(phonebook.toString());
            }
        } else {
            System.out.println("Không tìm thấy số điện thoại này với danh bạ trong hệ thống!!!");
        }
    }

    private void writeDataToFile() {
        System.out.println("Bạn có muốn ghi lại toàn bộ file không ??");
        System.out.println("Nhập Y để tiếp tục");
        String check = scanner.nextLine();
        if (!check.equals("Y")) {
            return;
        }
        List<Phonebook> phonebookList = phonebookManagement.getPhonebooks();
        try {
            FileWriter fileWriter = new FileWriter("phonebook.csv");
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (Phonebook phonebook: phonebookList ) {
                bw.write(String.valueOf(phonebook));
                bw.newLine();
            }
            bw.close();
            System.out.println("Ghi vào file thành công");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataToFile() {
        try {
            FileReader fileReader = new FileReader("phonebook.csv");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(Arrays.toString(line.split(",")));
            }
            br.close();
        } catch (IOException ie) {
            System.err.println("Fie không tồn tại or nội dung có lỗi!");
        }
    }


    public Phonebook creatPhoneBook() {
        System.out.println("---- Thêm danh bạ mới ---");
        String phoneNumber = inputPhoneNumber();
        System.out.println("Nhập nhóm danh bạ: ");
        String group = scanner.nextLine();
        System.out.println("Nhập họ tên: ");
        String name = scanner.nextLine();
        System.out.println("Nhập giới tính: ");
        String gender = scanner.nextLine();
        System.out.println("Nhập địa chỉ: ");
        String address = scanner.nextLine();
        System.out.println("Nhập ngày sinh: ");
        String dateOfBirth = scanner.nextLine();
        String email = inputEmail();
        return new Phonebook(phoneNumber, group, name, gender, address, dateOfBirth, email);
    }


    private String inputEmail() {
        String email;
        String EMAIL = "^[a-zA-Z]+[A-Za-z0-9]*@[a-zA-Z]+(\\.com)$";
        Pattern pattern = Pattern.compile(EMAIL);
        Matcher matcher;
        do {
            System.out.println("Nhập email: ");
            email = scanner.nextLine();
            matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Email không hợp lệ! ");
                System.out.println("Vui lòng nhập lại! ");
            }
        } while (!matcher.matches());
        return email;
    }

    private String inputPhoneNumber() {
        String phoneNumber;
        String PHONE_NUMBER = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(PHONE_NUMBER);
        Matcher matcher;
        do {
            System.out.println("Nhập số điện thoại: ");
            phoneNumber = scanner.nextLine();
            matcher = pattern.matcher(phoneNumber);
            if (!matcher.matches()) {
                System.out.println("Số điện thoại bạn nhập không hợp lệ!");
                System.out.println("Vui lòng nhập lại!");
            }
        } while (!matcher.matches());
        return phoneNumber;
    }

    public void menuSearch() {
        System.out.println("Chương trình tìm kiếm danh bạ");
        System.out.println("1. Tìm theo tên");
        System.out.println("2. Tìm theo số điện thoại");
        System.out.println("0. Thoát");
    }
    public void menu() {
        System.out.println("---- CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ ----");
        System.out.println("1. Xem danh sách");
        System.out.println("2. Thêm mới");
        System.out.println("3. Cập nhật");
        System.out.println("4. Xóa");
        System.out.println("5. Tìm kiếm ");
        System.out.println("6. Đọc từ file");
        System.out.println("7. Ghi vào file");
        System.out.println("8. Thoát");
    }
}
