import { useEffect, useState } from 'react';

const useImageBox = (imageUrl: string | undefined) => {
  const [imageSrcUrl, setImageSrcUrl] = useState('');

  const onChangeImg = (e: React.FormEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    if (!input.files?.length) {
      return;
    }

    const file = input.files[0];
    const src = URL.createObjectURL(file);

    setImageSrcUrl(src);
  };

  useEffect(() => {
    if (imageUrl) {
      setImageSrcUrl(imageUrl);
    }
  }, [imageUrl]);

  return { imageSrcUrl, onChangeImg };
};

export default useImageBox;
