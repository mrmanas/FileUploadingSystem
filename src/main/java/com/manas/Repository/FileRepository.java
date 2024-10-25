package com.manas.Repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.manas.MetaData.UploadedFile;

public interface FileRepository extends MongoRepository<UploadedFile, String> {
    List<UploadedFile> findByFilenameContainingIgnoreCase(String filename, Sort sort);
}
