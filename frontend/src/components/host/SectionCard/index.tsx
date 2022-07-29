import { useState } from 'react';

import Button from '@/components/common/Button';
import TaskBox from '@/components/host/TaskBox';

import useEditInput from '@/hooks/useEditInput';

import { SectionType } from '@/types';

import styles from './styles';

interface SectionCardProps {
  section: SectionType;
  sectionIndex: number;
  createTask: (sectionIndex: number) => void;
  editSection: (sectionIndex: number, value: string) => void;
  editTask: (sectionIndex: number, taskIndex: number, value: string) => void;
  deleteTask: (sectionIndex: number, taskIndex: number) => void;
  deleteSection: (sectionIndex: number) => void;
}

const SectionCard: React.FC<SectionCardProps> = ({
  section,
  sectionIndex,
  createTask,
  editSection,
  editTask,
  deleteTask,
  deleteSection,
}) => {
  const [sectionName, setSectionName] = useState(section.name);

  const { isEditing, inputRef, confirmInput, editInput: onClickEdit } = useEditInput();

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

  return (
    <div css={styles.container}>
      <form css={styles.titleWrapper} onSubmit={onSubmit}>
        {isEditing ? (
          <>
            <input css={styles.input} ref={inputRef} value={sectionName} onChange={onChange} />
            <Button type="submit" css={styles.confirmButton}>
              확인
            </Button>
          </>
        ) : (
          <>
            <span onClick={onClickEdit}>{sectionName}</span>
            <div>
              <Button css={styles.editButton} onClick={onClickEdit}>
                이름 변경
              </Button>
              <Button css={styles.deleteButton} onClick={onClickDelete}>
                삭제
              </Button>
            </div>
          </>
        )}
      </form>
      {section.tasks.map((task, taskIndex) => (
        <TaskBox
          task={task}
          sectionIndex={sectionIndex}
          taskIndex={taskIndex}
          editTask={editTask}
          deleteTask={deleteTask}
          key={task.id}
        />
      ))}
      <button css={styles.newTaskButton} onClick={onClickCreate}>
        + 새 작업 추가
      </button>
    </div>
  );
};

export default SectionCard;
