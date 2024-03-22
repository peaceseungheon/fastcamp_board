from locust import HttpUser, task, between
import random

class AddPosts(HttpUser):
  waite_time = between(1, 2)

  def on_start(self):
    self.client.post("/users/sign-in", json={"userId": "test1", "password": "1234"})

  @task
  def add_post(self):
    self.client.post("/posts", json={
      "name" : "테스트 게시글" + str(random.randint(100000, 200000)),
      "contents" : "테스트 컨텐츠" + str(random.randint(100000, 200000)),
      "categoryNo": random.randint(1,2),
      "isNotice": False,
      "views" : 0
    })