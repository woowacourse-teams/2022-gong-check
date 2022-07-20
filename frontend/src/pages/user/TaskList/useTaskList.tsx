import { useMemo } from 'react';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';

import apis from '@/apis';

const useTaskList = () => {
  const { openModal } = useModal();
  const { spaceId, jobId } = useParams();
  const { goPreviousPage } = useGoPreviousPage();

  const { data, refetch: getSections } = useQuery(['sections', jobId], () => apis.getTasks({ jobId }), {
    suspense: true,
  });

  const { data: spaceData } = useQuery(['space', jobId], () => apis.getSpace({ spaceId }), {
    suspense: true,
  });

  const onClickButton = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    openModal(
      <NameModal
        title="체크리스트 제출"
        detail="확인 버튼을 누르면 제출됩니다."
        placeholder="이름을 입력해주세요."
        buttonText="확인"
        jobId={jobId}
      />
    );
  };

  if (!data?.sections.length) return { isNotData: true };

  const { sections } = data;
  const tasks = useMemo(() => sections.map(section => section.tasks.map(task => task.checked)), [sections]);
  const checkList = useMemo(
    () =>
      tasks.reduce((prev, curv) => {
        return prev.concat(...curv);
      }, []),
    [tasks]
  );
  const totalCount = useMemo(() => checkList.length, [checkList]);
  const checkCount = useMemo(() => checkList.filter(check => check === true).length, [checkList]);
  const percent = useMemo(() => Math.ceil((checkCount / totalCount) * 100), [checkCount, totalCount]);
  const isAllChecked = totalCount === checkCount;

  return { data, spaceData, getSections, onClickButton, goPreviousPage, totalCount, checkCount, percent, isAllChecked };
};

export default useTaskList;
