import { useNavigate } from 'react-router-dom';

import apis from '@/apis';

const useJobCard = (id: number) => {
  const navigate = useNavigate();

  const onClickJobCard = async () => {
    const { active } = await apis.getJobActive({ jobId: id });
    if (!active) {
      await apis.postNewTasks({ jobId: id });
    }
    navigate(id.toString());
  };
  return { onClickJobCard };
};

export default useJobCard;
