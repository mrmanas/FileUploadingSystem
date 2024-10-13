package com.manas.Repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.manas.MetaData.UploadedFile;

public interface FileRepository extends MongoRepository<UploadedFile, String> {
}

