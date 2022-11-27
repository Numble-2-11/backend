package com.community.numble.app.board.controller;

import com.community.numble.app.board.domain.Post;
import com.community.numble.app.board.domain.PostCategory;
import com.community.numble.app.board.dto.PostDto.CreatePostPhotoRequest;
import com.community.numble.app.board.dto.PostDto.CreatePostPhotoResponse;
import com.community.numble.app.board.dto.PostDto.PostAllDto;
import com.community.numble.app.board.dto.PostDto.PostOneDto;
import com.community.numble.app.board.dto.PostDto.PostResult;
import com.community.numble.app.board.service.MemberService;
import com.community.numble.app.board.service.PostService;
import com.community.numble.app.board.service.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시물 api")
@RequestMapping("/api/v1") //버전명시(이전버전도 사용 분리)
public class PostApiController {

	private final PostService postService;
	private final UploadFileService uploadFileService;


	//카테고리 api


	@GetMapping("/post")
	@Operation(summary = "모든게시물 가져오기", description = "모든 게시물을 가져오는 메서드입니다.")
	public ResponseEntity getAllPost(){
		List<Post> findPosts = postService.findPost();
		List<PostAllDto> collect = findPosts.stream()
			.map(m ->new PostAllDto(m.getId(),m.getTitle(),m.getContent(),m.getLocation(),m.getType(),m.getMember().getId(),
				uploadFileService.findFirstImage(m.getId()),m.getCreateData(),m.getModifiedDate()))
			.collect(Collectors.toList());
		PostResult postResult = new PostResult(collect.size(),collect);
		return ResponseEntity.status(HttpStatus.OK).body(postResult);
	}

	@GetMapping("/category")
	@Operation(summary = "카테고리 가져오기 메서드", description = "카테고리 가져오기 메서드입니다.")
	public ResponseEntity getAllCategory(){
		Map<String, Object> enums = new LinkedHashMap<>();
		Class categoryType = PostCategory.class;
		enums.put("category", Arrays.stream(categoryType.getEnumConstants()).map(m -> postService.createType(m.toString())));
		return ResponseEntity.status(HttpStatus.OK).body(enums);
	}

	@GetMapping("post/{id}")
	@Operation(summary = "게시물 하나 PostId로 가져오기 메서드", description = "게시물 하나 id로 가져오기 메서드입니다.")
	public ResponseEntity getPostId(@PathVariable("id") Long id){
		Post post = postService.findOne(id);
		PostOneDto postOneDto = PostOneDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.location(post.getLocation())
			.type(post.getType())
			.memberId(post.getMember().getId())
			.allImageId(uploadFileService.findAllImage(post.getId()))
			.createTime(post.getCreateData())
			.modifiedTime(post.getModifiedDate())
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(postOneDto);
	}

	@PostMapping("/post")
	@Operation(summary = "게시물 업로드 메서드", description = "게시물 업로드 메서드입니다.")
	public ResponseEntity savePostPhoto(@RequestPart(value = "file",required = false) MultipartFile[] imageFiles,
		@RequestPart(value = "postinfo") CreatePostPhotoRequest request) {
		CreatePostPhotoResponse createPostPhotoResponse = new CreatePostPhotoResponse(uploadFileService.savePostImage(imageFiles,request));
		return ResponseEntity.status(HttpStatus.OK).body(createPostPhotoResponse);
	}

	//파일명 파일경로:타입,날짜별 파일타입 저장되는다x른파일이름:UUID바뀐거도 저장 , 확장자 ,파일사이즈,생성날자,수정날짜

	@GetMapping("/post/delete/{id}")
	@Operation(summary = "게시물 postId로 삭제 메서드", description = "게시물 id로 삭제 메서드입니다.")
	public ResponseEntity deletePost(@PathVariable("id") Long id){
		postService.deletePost(id);
		return ResponseEntity.status(HttpStatus.OK).body("delete");

	}

	@PostMapping("/post/update/{id}")
	@Operation(summary = "게시물 postId로 수정 메서드", description = "게시물 postId로 수정 메서드입니다.")
	public ResponseEntity updatePost(@PathVariable("id") Long currentid,@RequestPart(value = "file",required = false) MultipartFile[] imageFiles,
		@RequestPart(value = "postinfo") CreatePostPhotoRequest request){
		CreatePostPhotoResponse createPostPhotoResponse = new CreatePostPhotoResponse(uploadFileService.savePostImage(imageFiles,request,currentid));
		return ResponseEntity.status(HttpStatus.OK).body(createPostPhotoResponse);

	}


	@GetMapping(
		value = "/postimage/{id}",
		produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE}
	)
	@Operation(summary = "이미지 하나 imageId로 가져오기 메서드", description = "이미지 하나 imageId로 가져오기 메서드입니다.")
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable("id") Long imageid) throws IOException {

		InputStream in = this.getClass().getResourceAsStream(uploadFileService.findFilePath(imageid).replace('\\', '/'));
		return IOUtils.toByteArray(in);
	}




//	@PostMapping("/post")
//	public ResponseEntity savePost(@RequestBody CreatePostRequest request) {
//		Post post = new Post();
//		post.setTitle(request.getTitle());
//		post.setContent(request.getContent());
//		post.setLocation(request.getLocation());
//		post.createType(request.getType());
//		post.setMember(memberService.findOne(request.getMember()));
//		Long id = postService.post(post);
//
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CreatePostResponse(id));
//	}


}

