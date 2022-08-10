import { useNavigate } from 'react-router-dom';

import { SpaceType } from '@/types';

const useSpaceInfoDisplayBox = (spaceData: SpaceType) => {
  const navigate = useNavigate();

  const onClickEditSpaceInfo = () => {
    navigate(`/host/manage/${spaceData.id}/spaceUpdate`);
  };

  return { onClickEditSpaceInfo };
};

export default useSpaceInfoDisplayBox;
