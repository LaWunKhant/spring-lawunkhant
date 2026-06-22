package com.cmps.spring.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Star imports pull in all Entities and Repositories automatically
import com.cmps.spring.entity.*;
import com.cmps.spring.repository.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/relation")
@RestController
@Controller
public class RelationController {

	// Dependency Injection for all Repositories via Lombok @RequiredArgsConstructor
	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;
	private final PostRepository postRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final StudentRepository studentRepository;
	private final ExamRepository examRepository;

	/**
	 * MemberからProfileへのリレーション確認
	 */
	@GetMapping("/mem-to-pro")
	public String memberToProfile() {
		Optional<Member> memberOpt = memberRepository.findById(1);
		if (memberOpt.isEmpty()) return "Member ID 1 not found.";
		Member member = memberOpt.get();

		Profile profile = member.getProfile();

		String text = "メンバーは" + member.getName() + "<br>";
		text += profile.getAddress() + "に住んでいます";

		return text;
	}

	/**
	 * ProfileからMemberへのリレーション確認
	 */
	@GetMapping("/pro-to-mem")
	public String profileToMember() {
		Optional<Profile> profOpt = profileRepository.findById(2);
		if (profOpt.isEmpty()) return "Profile ID 2 not found.";
		Profile prof = profOpt.get();

		Member member = prof.getMember();

		String text = "プロフィールNo. " + prof.getId() + "<br>";
		text += member.getName() + "さんのプロフィールです";

		return text;
	}
	
	/**
	 * PostからMemberへのリレーション確認
	 */
	@GetMapping("/post-to-mem")
	public String postToMember() {
		Optional<Post> postOpt = postRepository.findById(2);
		if (postOpt.isEmpty()) return "Post ID 2 not found.";
		Post post = postOpt.get();

		Member member = post.getMember();

		String text = "投稿ID:" + post.getId();
		text += " , タイトル:" + post.getTitle() + " , 本文:" + post.getBody() + "<br>";
		text += member.getName() + "さんの投稿です";

		return text;
	}

	/**
	 * OrderからProduct（多対多中間テーブル）へのリレーション確認
	 */
	@GetMapping("/order-test")
	public String testOrderRelation() {
		Optional<Order> orderOpt = orderRepository.findById(2);
		if (orderOpt.isEmpty()) {
			return "Order ID 2 not found in the database.";
		}
		Order order = orderOpt.get();

		StringBuilder html = new StringBuilder();
		html.append("<h3>注文ID: ").append(order.getId()).append(" の詳細</h3>");

		// Loop through the intermediate items mapping to products
		for (OrderProduct op : order.getOrderProducts()) {
			html.append("商品名: ").append(op.getProduct().getName())
			    .append(" | 数量: ").append(op.getQuantity())
			    .append("<br>");
		}

		return html.toString();
	}
	
	@GetMapping("/all-students-exams")  // ✅ New endpoint
	public String getAllStudentsExams() {
	    List<Student> students = studentRepository.findAll();  // ✅ Change: Get ALL students
	    
	    StringBuilder html = new StringBuilder();
	    html.append("<h3>全生徒の試験結果一覧</h3>");
	    
	    for (Student student : students) {  // ✅ Loop through each student
	        html.append("<h4>").append(student.getName()).append("</h4>");
	        
	        for (StudentExam se : student.getStudentExams()) {  // Loop through exams
	            html.append("試験名: ").append(se.getExam().getTitle())
	                .append(" | 点数: ").append(se.getScore()).append("点")
	                .append("<br>");
	        }
	    }
	    return html.toString();
	}
	
	@GetMapping("/exam-students")
	public String getExamStudents() {
	    // ID 3 の試験（2022夏季期末テスト）を取得
	    Optional<Exam> examOpt = examRepository.findById(3);
	    if (examOpt.isEmpty()) {
	        return "試験ID: 3 が見つかりません。";
	    }
	    Exam exam = examOpt.get();

	    StringBuilder html = new StringBuilder();
	    html.append("<h3>試験名: ").append(exam.getTitle()).append(" の受験者一覧</h3>");

	    // 中間テーブルを経由して生徒名と点数をループ処理
	    for (StudentExam se : exam.getStudentExams()) {
	        html.append("生徒氏名: ").append(se.getStudent().getName())
	            .append(" | 点数: ").append(se.getScore()).append("点")
	            .append("<br>");
	    }

	    return html.toString();
	}
}