import { useNavigate } from 'react-router-dom';

import apis from '@/apis';

import { ID } from '@/types';

const useJobCard = (id: ID) => {
  const navigate = useNavigate();

  const onClickJobCard = async () => {
    const { active } = await apis.getJobActive(id);
    if (!active) {
      await apis.postNewRunningTasks(id);
    }
    navigate(id.toString());
  };
  return { onClickJobCard };
};

export default useJobCard;
