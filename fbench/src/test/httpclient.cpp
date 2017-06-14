// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
#include <httpclient/httpclient.h>
#include <iostream>
#include <thread>

int
main(int argc, char **argv)
{
  if (argc < 4) {
    printf("usage: httpclient <host> <port> <url> [keep-alive]\n");
    return 1;
  }

  HTTPClient           *client;
  ssize_t               len;

  if(argc == 4) {
    client = new HTTPClient(argv[1], atoi(argv[2]), false, true);
  } else {
    client = new HTTPClient(argv[1], atoi(argv[2]), true, true);
  }

  std::ostream * output = & std::cout;

  if ((len = client->Fetch(argv[3], output).ResultSize()) >= 0) {
    printf("SUCCESS!\n");
    printf("LENGTH: %ld\n", len);
  } else {
      printf("ERROR: could not fetch URL content.\n");
  }
  if ((len = client->Fetch(argv[3], output).ResultSize()) >= 0) {
    printf("SUCCESS!\n");
    printf("LENGTH: %ld\n", len);
  } else {
      printf("ERROR: could not fetch URL content.\n");
  }

  std::this_thread::sleep_for(std::chrono::seconds(20));

  if ((len = client->Fetch(argv[3], output).ResultSize()) >= 0) {
    printf("SUCCESS!\n");
    printf("LENGTH: %ld\n", len);
  } else {
      printf("ERROR: could not fetch URL content.\n");
  }
  if ((len = client->Fetch(argv[3], output).ResultSize()) >= 0) {
    printf("SUCCESS!\n");
    printf("LENGTH: %ld\n", len);
  } else {
      printf("ERROR: could not fetch URL content.\n");
  }
  printf("REUSE COUNT: %" PRIu64 "\n", client->GetReuseCount());
  return 0;
}
