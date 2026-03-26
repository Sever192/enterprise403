package com.example.customermanagement.config;

import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.Role;
import com.example.customermanagement.entity.User;
import com.example.customermanagement.repository.CustomerRepository;
import com.example.customermanagement.repository.RoleRepository;
import com.example.customermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========================================");
        System.out.println("DataInitializer STARTED!");
        System.out.println("========================================");

        // ==================== 1. СОЗДАНИЕ РОЛЕЙ ====================
        createRolesIfNotExist();

        // ==================== 2. СОЗДАНИЕ ТЕСТОВЫХ ПОЛЬЗОВАТЕЛЕЙ ====================
        createTestUsersIfNotExist();

        // ==================== 3. СОЗДАНИЕ КЛИЕНТОВ ====================
        createCustomersIfNotExist();

        System.out.println("========================================");
        System.out.println("DataInitializer FINISHED!");
        System.out.println("========================================");
    }

    /**
     * Создание ролей, если они еще не существуют
     */
    private void createRolesIfNotExist() {
        System.out.println("\n--- Проверка и создание ролей ---");

        // Создание роли USER
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("✓ Роль ROLE_USER создана");
        } else {
            System.out.println("• Роль ROLE_USER уже существует");
        }

        // Создание роли ADMIN
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println("✓ Роль ROLE_ADMIN создана");
        } else {
            System.out.println("• Роль ROLE_ADMIN уже существует");
        }
    }

    /**
     * Создание тестовых пользователей для авторизации
     */
    private void createTestUsersIfNotExist() {
        System.out.println("\n--- Проверка и создание тестовых пользователей ---");

        if (userRepository.count() == 0) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER не найдена"));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN не найдена"));

            // Пользователь с правами ADMIN
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminUser.setRoles(adminRoles);
            userRepository.save(adminUser);
            System.out.println("✓ Администратор создан: username='admin', password='admin123'");

            // Пользователь с правами USER
            User normalUser = new User();
            normalUser.setUsername("user");
            normalUser.setPassword(passwordEncoder.encode("user123"));
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            normalUser.setRoles(userRoles);
            userRepository.save(normalUser);
            System.out.println("✓ Обычный пользователь создан: username='user', password='user123'");

            // Дополнительный пользователь для тестирования
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setPassword(passwordEncoder.encode("test123"));
            Set<Role> testRoles = new HashSet<>();
            testRoles.add(userRole);
            testUser.setRoles(testRoles);
            userRepository.save(testUser);
            System.out.println("✓ Тестовый пользователь создан: username='testuser', password='test123'");

            System.out.println("\n📝 ИТОГО создано пользователей: " + userRepository.count());
        } else {
            System.out.println("• Пользователи уже существуют. Всего: " + userRepository.count());
            // Выводим список существующих пользователей для информации
            userRepository.findAll().forEach(user ->
                    System.out.println("  - " + user.getUsername() + " (ролей: " + user.getRoles().size() + ")")
            );
        }
    }

    /**
     * Создание клиентов, если их еще нет
     */
    private void createCustomersIfNotExist() {
        System.out.println("\n--- Проверка и создание клиентов ---");
        System.out.println("Текущее количество клиентов: " + customerRepository.count());

        if (customerRepository.count() == 0) {
            // Клиент 1
            Customer c1 = new Customer();
            c1.setFirstName("Иван");
            c1.setLastName("Петров");
            c1.setEmail("ivan@example.com");
            c1.setPhone("+7 (999) 123-45-67");
            c1.setAddress("г. Москва, ул. Тверская, д. 10");
            customerRepository.save(c1);
            System.out.println("✓ Клиент 1: Иван Петров");

            // Клиент 2
            Customer c2 = new Customer();
            c2.setFirstName("Мария");
            c2.setLastName("Иванова");
            c2.setEmail("maria.ivanova@example.com");
            c2.setPhone("+7 (888) 765-43-21");
            c2.setAddress("г. Санкт-Петербург, ул. Пушкина, д. 15");
            customerRepository.save(c2);
            System.out.println("✓ Клиент 2: Мария Иванова");

            // Клиент 3
            Customer c3 = new Customer();
            c3.setFirstName("Петр");
            c3.setLastName("Сидоров");
            c3.setEmail("petr.sidorov@example.com");
            c3.setPhone("+7 (777) 111-22-33");
            c3.setAddress("г. Новосибирск, ул. Гагарина, д. 25");
            customerRepository.save(c3);
            System.out.println("✓ Клиент 3: Петр Сидоров");

            // Клиент 4
            Customer c4 = new Customer();
            c4.setFirstName("Елена");
            c4.setLastName("Козлова");
            c4.setEmail("elena.kozlova@example.com");
            c4.setPhone("+7 (916) 555-66-77");
            c4.setAddress("г. Екатеринбург, ул. Ленина, д. 50");
            customerRepository.save(c4);
            System.out.println("✓ Клиент 4: Елена Козлова");

            // Клиент 5
            Customer c5 = new Customer();
            c5.setFirstName("Алексей");
            c5.setLastName("Смирнов");
            c5.setEmail("alexey.smirnov@example.com");
            c5.setPhone("+7 (495) 888-99-00");
            c5.setAddress("г. Казань, ул. Баумана, д. 7");
            customerRepository.save(c5);
            System.out.println("✓ Клиент 5: Алексей Смирнов");

            System.out.println("\n📝 ИТОГО создано клиентов: " + customerRepository.count());
        } else {
            System.out.println("• Клиенты уже существуют. Всего: " + customerRepository.count());
        }
    }
}