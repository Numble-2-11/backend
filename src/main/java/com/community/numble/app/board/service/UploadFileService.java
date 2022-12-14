package com.community.numble.app.board.service;

import com.community.numble.app.board.domain.*;
import com.community.numble.app.board.dto.PostDto.*;
import com.community.numble.app.board.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.text.*;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadFileService {

	private final UploadFileRepository uploadFileRepository;
	private final MemberService memberService;
	private final PostService postService;


	public long upload(UploadFile uploadFile){
		uploadFileRepository.save(uploadFile);
		return uploadFile.getId();
	}

	public void deletefile(Post post){
		uploadFileRepository.deletePostFile(post);
	}

	public List<UploadFile> findFilePost(Long postId) {
		return uploadFileRepository.findByPost(postId);

	}

	public String findFilePath(Long id){
		return uploadFileRepository.findIdPath(id);
	}


	public Long savePostImage(MultipartFile[] imageFiles, CreatePostPhotoRequest request){
		String uploadFolder = "C:\\Users\\aa\\Desktop\\numble11\\backend\\src\\main\\resources\\media\\postUplode";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		File uploadPath = new File(uploadFolder, datePath);
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		Post post = new Post();
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());
		post.setLocation(request.getLocation());
		//post.createType(request.getType());
		post.setMember(memberService.findOne(request.getMember()));

		Long id = postService.post(post);

		if(!imageFiles[0].isEmpty()) {
			for (MultipartFile multipartFile : imageFiles) {
				String uploadFileName = multipartFile.getOriginalFilename();
				UploadFile uploadFile = new UploadFile();
				uploadFile.setImageFiles(uploadFileName);
				String[] pathCut = uploadPath.getPath().split("resources");
				uploadFile.setPath(pathCut[1]+"\\"+uploadFileName);
				uploadFile.setPost(post);


				/* ?????? ??????, ?????? ????????? ?????? File ?????? */
				File saveFile = new File(uploadPath, uploadFileName);

				/* ?????? ?????? */
				try {
					multipartFile.transferTo(saveFile);
					Long idFile = upload(uploadFile);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	public Long savePostImage(MultipartFile[] imageFiles, CreatePostPhotoRequest request, Long currentid){
		String uploadFolder = "C:\\Users\\aa\\Desktop\\numble11\\backend\\src\\main\\resources\\media\\postUplode";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		File uploadPath = new File(uploadFolder, datePath);
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		Post post = postService.findOne(currentid);
		deletefile(post);
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());
		post.setLocation(request.getLocation());
		//post.createType(request.getType());
		post.setMember(memberService.findOne(request.getMember()));

		Long id = postService.post(post);




		if(!imageFiles[0].isEmpty()) {
			for (MultipartFile multipartFile : imageFiles) {
				String uploadFileName = multipartFile.getOriginalFilename();
				UploadFile uploadFile = new UploadFile();
				uploadFile.setImageFiles(uploadFileName);
				uploadFile.setPath(uploadPath.getPath()+"\\"+uploadFileName);
				uploadFile.setPost(post);


				/* ?????? ??????, ?????? ????????? ?????? File ?????? */
				File saveFile = new File(uploadPath, uploadFileName);

				/* ?????? ?????? */
				try {
					multipartFile.transferTo(saveFile);
					Long idFile = upload(uploadFile);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	public Long findFirstImage(Long postId){
		return uploadFileRepository.findIdFirst(postId);
	}

	public List<Long> findAllImage(Long postId){
		return uploadFileRepository.findIdAll(postId);
	}
}
