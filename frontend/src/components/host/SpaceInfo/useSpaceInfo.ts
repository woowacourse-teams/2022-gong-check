import { useMemo, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const useSpaceInfo = (data?: { name: string; imageUrl: string; id: number }) => {
  const navigate = useNavigate();
  const labelRef = useRef(null);
  const [imageUrl, setImageUrl] = useState('');
  const [isActiveSubmit, setIsActiveSubmit] = useState(false);

  const isExistimageUrl = useMemo(() => !!imageUrl, [imageUrl]);

  const onChangeImg = (e: React.FormEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    if (!input.files?.length || !labelRef.current) {
      return;
    }

    const file = input.files[0];
    const src = URL.createObjectURL(file);

    setImageUrl(src);
  };

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const onClickEditSpaceInfo = () => {
    navigate(`/host/manage/${data?.id}/spaceModify`);
  };

  return { imageUrl, labelRef, isActiveSubmit, isExistimageUrl, onChangeImg, onChangeSpaceName, onClickEditSpaceInfo };
};

export default useSpaceInfo;
