import SectionDetailModal from '../SectionDetailModal';

import useModal from '@/hooks/useModal';
import useSections from '@/hooks/useSections';

const useTaskBox = (taskIndex: number, sectionIndex: number) => {
  const { getTaskInfo, editTask, deleteTask } = useSections();
  const { openModal } = useModal();

  const hasTaskDetailInfo = () => {
    const { imageUrl, description } = getTaskInfo(sectionIndex, taskIndex);
    return !!imageUrl || !!description;
  };

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    editTask(sectionIndex, taskIndex, e.target.value);
  };

  const onClickDeleteButton = () => {
    deleteTask(sectionIndex, taskIndex);
  };

  const onClickTaskDetail = () => {
    const previousImageUrl = getTaskInfo(sectionIndex, taskIndex).imageUrl;
    const previousDescription = getTaskInfo(sectionIndex, taskIndex).description;

    openModal(
      <SectionDetailModal
        target="task"
        sectionIndex={sectionIndex}
        taskIndex={taskIndex}
        previousImageUrl={previousImageUrl}
        previousDescription={previousDescription}
      />
    );
  };

  return { hasTaskDetailInfo, onChangeInput, onClickDeleteButton, onClickTaskDetail };
};

export default useTaskBox;
