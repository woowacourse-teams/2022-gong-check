import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import JobControl from '@/components/host/JobControl';
import SectionCard from '@/components/host/SectionCard';

import useSections from '@/hooks/useSections';

import apiJobs from '@/apis/job';

import { SectionType } from '@/types';
import { ApiError } from '@/types/apis';

import styles from './styles';

type MutationParams = { spaceId: string | number | undefined; newJobName: string; sections: SectionType[] };

const JobCreate: React.FC = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  const [newJobName, setNewJobName] = useState('새 업무');

  const { sections, createSection, editSection, deleteSection, createTask, editTask, deleteTask } = useSections();

  const { mutate: createNewJob } = useMutation(
    ({ spaceId, newJobName, sections }: MutationParams) => apiJobs.postNewJob(spaceId, newJobName, sections),
    {
      onSuccess: () => {
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: ApiError) => {
        alert(err.response?.data.message);
        navigate('/host/manage/spaceCreate');
      },
    }
  );

  const onChangeJobName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewJobName(e.target.value);
  };

  const onClickCreateNewJob = () => {
    createNewJob({ spaceId, newJobName, sections });
  };

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <JobControl jobName={newJobName} onChangeJobName={onChangeJobName} onClickCreateNewJob={onClickCreateNewJob} />
        <div css={styles.grid}>
          {sections.map((section, sectionIndex) => (
            <SectionCard
              section={section}
              sectionIndex={sectionIndex}
              createTask={createTask}
              editSection={editSection}
              editTask={editTask}
              deleteTask={deleteTask}
              deleteSection={deleteSection}
              key={section.id}
            />
          ))}
          <div css={styles.createCard} onClick={createSection}>
            +
          </div>
        </div>
      </div>
    </div>
  );
};
export default JobCreate;
