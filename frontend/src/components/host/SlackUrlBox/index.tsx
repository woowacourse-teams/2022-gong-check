import React, { useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';

import slackApi from '@/apis/slack';

import styles from './styles';

const SLACK_URL_DATA = {
  slackUrl: 'https://sdfgsd.sdf',
};

interface SlackUrlBoxProps {
  jobName: string;
  jobId: number | string;
}

const SlackUrlBox: React.FC<SlackUrlBoxProps> = ({ jobName, jobId }) => {
  // const { data } = useQuery(['slackUrl', jobId], () => slackApi.getSlackUrl(jobId), {
  //   suspense: true,
  // });

  const { mutate: putSlackUrl } = useMutation((url: string) => slackApi.putSlackUrl(jobId, url));

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.target as HTMLFormElement;
    const { value: slackUrl } = form['slackUrl'];

    putSlackUrl(slackUrl);
  };

  // if (!data) return <div />;
  // <Input css={styles.input} value={data.slackUrl} placeholder="Slack URL" />;

  return (
    <form css={styles.slackUrlBox} onSubmit={onSubmit}>
      <span css={styles.jobName}>{jobName}</span>
      <Input css={styles.input} name="slackUrl" defaultValue={SLACK_URL_DATA.slackUrl} placeholder="Slack URL" />
      <Button type="submit" css={styles.button}>
        편집
      </Button>
    </form>
  );
};

export default SlackUrlBox;
