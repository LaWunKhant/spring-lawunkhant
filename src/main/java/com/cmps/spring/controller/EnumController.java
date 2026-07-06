package com.cmps.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.entity.OrderInfo;
import com.cmps.spring.entity.Staff;
import com.cmps.spring.enums.Gender;
import com.cmps.spring.enums.OrderStatus;
import com.cmps.spring.form.OrderInfoForm;
import com.cmps.spring.form.StaffForm;
import com.cmps.spring.service.OrderInfoService;
import com.cmps.spring.service.StaffService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/enum")
@Controller
public class EnumController {

    private final StaffService staffService;
    private final OrderInfoService orderInfoService;

    // ========== STAFF ENUM FEATURES ==========

    /**
     * スタッフ一覧表示（Gender Enumを表示）
     */
    @GetMapping("/staff/list")
    public String listStaffs(Model model) {
        List<Staff> staffs = staffService.findAll();
        model.addAttribute("staffs", staffs);
        return "enum/staff-list";
    }

    /**
     * スタッフ登録フォーム表示
     */
    @GetMapping("/staff/register")
    public String showStaffRegisterForm(Model model) {
        // Gender.values() でプルダウン用にすべてのGender定数を渡す
        model.addAttribute("staffForm", new StaffForm());
        model.addAttribute("staffGender", Gender.values());
        return "enum/staff-register";
    }

    /**
     * スタッフ登録処理（Enum定数として受け取る）
     */
    @PostMapping("/staff/register")
    public String registerStaff(@ModelAttribute StaffForm form, RedirectAttributes redirectAttributes) {
        Staff staff = new Staff();
        staff.setName(form.getName());
        staff.setGender(form.getGender());  // Enum型で直接受け取る
        
        staffService.save(staff);
        redirectAttributes.addFlashAttribute("successMessage", "スタッフを登録しました。");
        return "redirect:/enum/staff/list";
    }

    /**
     * スタッフ削除
     */
    @GetMapping("/staff/delete/{id}")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        staffService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "スタッフを削除しました。");
        return "redirect:/enum/staff/list";
    }

    // ========== ORDER ENUM FEATURES ==========

    /**
     * 注文一覧表示（OrderStatus Enumを表示）
     */
    @GetMapping("/order/list")
    public String listOrders(Model model) {
        List<OrderInfo> orders = orderInfoService.findAll();
        model.addAttribute("orders", orders);
        return "enum/order-list";
    }

    /**
     * 注文登録フォーム表示
     */
    @GetMapping("/order/register")
    public String showOrderRegisterForm(Model model) {
        // OrderStatus.values() でプルダウン用にすべてのOrderStatus定数を渡す
        model.addAttribute("orderInfoForm", new OrderInfoForm());
        model.addAttribute("orderStatus", OrderStatus.values());
        return "enum/order-register";
    }

    /**
     * 注文登録処理（数値として受け取り、Enumに変換）
     */
    @PostMapping("/order/register")
    public String registerOrder(@ModelAttribute OrderInfoForm form, RedirectAttributes redirectAttributes) {
        try {
            // 数値（Integer）からEnumオブジェクトへ「逆引き」して変換
            OrderStatus status = OrderStatus.fromCode(form.getStatusCode());

            OrderInfo orderInfo = OrderInfo.builder()
                    .totalPrice(form.getTotalPrice())
                    .status(status)  // ここでEnum型としてセット
                    .build();

            orderInfoService.save(orderInfo);
            redirectAttributes.addFlashAttribute("successMessage", "注文を登録しました。");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "無効なステータスコードです。");
        }
        return "redirect:/enum/order/list";
    }

    /**
     * 注文削除
     */
    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        orderInfoService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "注文を削除しました。");
        return "redirect:/enum/order/list";
    }
}