import useSections from '@/hooks/useSections';

const useTaskBox = (taskIndex: number, sectionIndex: number) => {
  const { editTask, deleteTask } = useSections();

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    editTask(sectionIndex, taskIndex, e.target.value);
  };

  const onClickDeleteButton = () => {
    deleteTask(sectionIndex, taskIndex);
  };

  return { onChangeInput, onClickDeleteButton };
};

export default useTaskBox;
