import { useEffect, useRef, useState } from 'react';

import Button from '@/components/common/Button';
import TaskBox from '@/components/host/TaskBox';

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
  const inputRef = useRef<HTMLInputElement>(null);

  const [sectionName, setSectionName] = useState(section.name);
  const [isEditing, setIsEditing] = useState(false);

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSectionName(e.target.value);
  };

  const onConfirmInput = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    editSection(sectionIndex, sectionName);
    setIsEditing(false);
  };

  const onClickEdit = () => {
    setIsEditing(true);
  };

  const onClickDelete = () => {
    deleteSection(sectionIndex);
  };

  const onClickCreate = () => {
    createTask(sectionIndex);
  };

  useEffect(() => {
    inputRef.current?.focus();
  }, [isEditing]);

  return (
    <div css={styles.container}>
      <form css={styles.titleWrapper} onSubmit={onConfirmInput}>
        {isEditing ? (
          <>
            <input css={styles.input} ref={inputRef} value={sectionName} onChange={onChange} />
            <Button type="submit" css={styles.confirmButton}>
              수정 확인
            </Button>
          </>
        ) : (
          <>
            <span>{sectionName}</span>
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
