import { useState } from 'react';

import useEditInput from '@/hooks/useEditInput';
import useSections from '@/hooks/useSections';

import { SectionType } from '@/types';

const useSectionCardDefault = (section: SectionType, sectionIndex: number) => {
  const [sectionName, setSectionName] = useState(section.name);

  const { isEditing, inputRef, confirmInput, editInput: onClickEdit } = useEditInput();

  const { editSection, deleteSection, createTask } = useSections();

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    confirmInput();
    editSection(sectionIndex, sectionName);
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSectionName(e.target.value);
  };

  const onClickDelete = () => {
    deleteSection(sectionIndex);
  };

  const onClickCreate = () => {
    createTask(sectionIndex);
  };

  return { sectionName, isEditing, inputRef, onClickEdit, onSubmit, onChange, onClickDelete, onClickCreate };
};

export default useSectionCardDefault;
