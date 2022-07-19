import { nanoid } from 'nanoid';
import { useState } from 'react';

import Button from '@/components/_common/Button';

import TaskBox from '@/components/host/TaskBox';

import styles from './styles';

type TaskType = { name: string }[];

type SectionType = { name: string; tasks: TaskType };

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
  const [isEditing, setIsEditing] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSectionName(e.target.value);
  };

  const handleClickConfirm = () => {
    editSection(sectionIndex, sectionName);
    setIsEditing(false);
  };

  const handleClickEdit = () => {
    setIsEditing(true);
  };

  const handleClickDelete = () => {
    deleteSection(sectionIndex);
  };

  const handleClickCreate = () => {
    createTask(sectionIndex);
  };

  return (
    <div css={styles.container}>
      <div css={styles.titleWrapper}>
        {isEditing ? (
          <>
            <input value={sectionName} onChange={handleChange} />
            <Button css={styles.confirmButton} onClick={handleClickConfirm}>
              수정 완료
            </Button>
          </>
        ) : (
          <>
            <span>{sectionName}</span>
            <div>
              <Button css={styles.editButton} onClick={handleClickEdit}>
                이름 수정
              </Button>
              <Button css={styles.deleteButton} onClick={handleClickDelete}>
                삭제
              </Button>
            </div>
          </>
        )}
      </div>
      {section.tasks.map((task, taskIndex) => (
        <TaskBox
          task={task}
          sectionIndex={sectionIndex}
          taskIndex={taskIndex}
          editTask={editTask}
          deleteTask={deleteTask}
          key={nanoid()}
        />
      ))}
      <button css={styles.newTaskButton} onClick={handleClickCreate}>
        + 새 작업 추가
      </button>
    </div>
  );
};

export default SectionCard;
