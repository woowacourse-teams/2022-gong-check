import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const useSpaceInfo = (data?: { name: string; imageUrl: string; id: number }, type?: 'read' | 'create' | 'update') => {
  const navigate = useNavigate();
  const [name, setName] = useState<string | undefined>('');

  const [isActiveSubmit, setIsActiveSubmit] = useState(type === 'update');

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const onClickEditSpaceInfo = () => {
    navigate(`/host/manage/${data?.id}/spaceUpdate`);
  };

  useEffect(() => {
    if (data?.name) {
      setName(data?.name);
    }
  }, [data]);

  return { name, isActiveSubmit, onChangeSpaceName, onClickEditSpaceInfo };
};

export default useSpaceInfo;
