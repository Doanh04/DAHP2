const getBase64 = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });
};

export const handleUploadChange = async (info, setBase64Image) => {
  const { fileList } = info;
  if (!fileList || fileList.length === 0) {
    setBase64Image(""); // Không có ảnh
    return;
  }

  const file = fileList[0].originFileObj;
  // Nếu người dùng chọn ảnh mới
  if (file) {
    const base64String = await getBase64(file);
    const pureBase64 = base64String.split(",")[1];
    setBase64Image(pureBase64);
  }
};
