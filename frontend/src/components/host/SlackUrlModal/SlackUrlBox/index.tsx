import { useMutation, useQuery } from 'react-query';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';

import useToast from '@/hooks/useToast';

import slackApi from '@/apis/slack';

import { ID } from '@/types';

import styles from './styles';

interface SlackUrlBoxProps {
  jobName: string;
  jobId: ID;
}

const SlackUrlBox: React.FC<SlackUrlBoxProps> = ({ jobName, jobId }) => {
  const { openToast } = useToast();

  const { data: slackUrlData } = useQuery(['slackUrl', jobId], () => slackApi.getSlackUrl(jobId));

  const { mutate: putSlackUrl } = useMutation((url: string) => slackApi.putSlackUrl(jobId, url), {
    onSuccess: () => {
      openToast('SUCCESS', 'URL이 수정되었습니다.');
    },
  });

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.target as HTMLFormElement;
    const { value: slackUrl } = form['slackUrl'];

    putSlackUrl(slackUrl);
  };

  if (!slackUrlData) return <></>;

  return (
    <div css={styles.slackUrlBox}>
      <label htmlFor="slackUrl" css={styles.label}>
        {jobName}
      </label>
      <form css={styles.form} onSubmit={onSubmit}>
        <Input name="slackUrl" css={styles.input} defaultValue={slackUrlData.slackUrl ?? ''} placeholder="Slack URL" />
        <Button type="submit" css={styles.button}>
          수정
        </Button>
      </form>
    </div>
  );
};

export default SlackUrlBox;
