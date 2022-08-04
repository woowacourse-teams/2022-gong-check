const checkFileSize = (file: File) => {
  const maxSize = 50 * 1024 * 1024;
  const fileSize = file.size;

  if (fileSize > maxSize) {
    alert('첨부파일 사이즈는 50MB 이내로 등록 가능합니다.');
    return false;
  }

  return true;
};

export default checkFileSize;
