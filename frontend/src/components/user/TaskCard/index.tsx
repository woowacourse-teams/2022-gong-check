import CheckBox from '@/components/common/Checkbox';

import apis from '@/apis';

import { ID, TaskType } from '@/types';

import styles from './styles';

type TaskCardProps = {
  tasks: TaskType[];
  getSections: () => void;
};

const TaskCard: React.FC<TaskCardProps> = ({ tasks, getSections }) => {
  const onClickCheckBox = async (
    e: React.MouseEvent<HTMLElement, MouseEvent> | React.ChangeEvent<HTMLElement>,
    id: ID
  ) => {
    e.preventDefault();
    await apis.postCheckTask({ taskId: id });
    getSections();
  };

  return (
    <div css={styles.taskCard}>
      {tasks.map((task, id) => (
        <div key={id} css={styles.task}>
          <CheckBox
            onChange={e => onClickCheckBox(e, task.id)}
            checked={task.checked || false}
            id={JSON.stringify(task.id)}
          />
          <span>{task.name}</span>
        </div>
      ))}
    </div>
  );
};

export default TaskCard;
