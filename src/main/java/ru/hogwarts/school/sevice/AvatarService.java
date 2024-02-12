package ru.hogwarts.school.sevice;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for uploadAvatar");
        Student student = studentService.findStudent(studentId);
//        хранит путь до директории с загружаемыми файлами.
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
//        Создаем нужную нам директорию для хранения данных и удаляем из нее файл, если он уже присутствует там.
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
//        конструкция нам нужна, чтобы следить за закрытием открытых ресурсов
        try (
//                чтение файла. Открываем входной поток командой avatarFile.getInputStream() и начинаем считывать данные
                InputStream is = avatarFile.getInputStream();
//                запись файла
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                буферизация для чтения не по байтно, а частями заданного размера
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                для записи
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
//            запустить сам процесс передачи данных методом transferTo.
            bis.transferTo(bos);
        }
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long id) {
        logger.info("Was invoked method for findAvatar");
        return avatarRepository.findAvatarById(id).orElseThrow();
    }

    public Collection<Avatar> getAvatarLimit(Integer pageNamber, Integer pageSize) {
        logger.info("Was invoked method for getAvatarLimit");
        PageRequest pageRequest = PageRequest.of(pageNamber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }


}
