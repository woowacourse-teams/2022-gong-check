import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const useSpaceInfo = (data?: { name: string; imageUrl: string; id: number }, type?: 'read' | 'create' | 'update') => {
  const navigate = useNavigate();

  const [isActiveSubmit, setIsActiveSubmit] = useState(type === 'update');

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const onClickEditSpaceInfo = () => {
    navigate(`/host/manage/${data?.id}/spaceUpdate`);
  };

  return { isActiveSubmit, onChangeSpaceName, onClickEditSpaceInfo };
};

export default useSpaceInfo;
