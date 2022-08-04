import useSections from '@/hooks/useSections';

const useSectionCardDefault = (sectionIndex: number) => {
  const { editSection, deleteSection, createTask } = useSections();

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    editSection(sectionIndex, e.target.value);
  };

  const onClickDelete = () => {
    deleteSection(sectionIndex);
  };

  const onClickCreate = () => {
    createTask(sectionIndex);
  };

  return { onChange, onClickDelete, onClickCreate };
};

export default useSectionCardDefault;
