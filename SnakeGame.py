import pygame
from pygame.math import Vector2
from random import randint

class FRUIT:
    def __init__(self):
        self.snake = SNAKE()
        self.pos = Vector2(15,10)

    # Kiểm tra sinh táo đến khi nào không trùng rắn
    def spawn_fruit(self):        
        if self.check_fruit():
            self.pos = Vector2(randint(0,cell_number-1),randint(0,cell_number-1))
        else:
            self.spawn_fruit()

    # Kiểm tra táo trùng rắn
    def check_fruit(self):
        for block in self.snake.body:
            if block == self.pos:
                return False
        return True

    def draw_fruit(self):
        fruit_img = pygame.image.load("graphics/apple.png").convert_alpha()
        fruit_rect = pygame.Rect(self.pos.x*cell_size,self.pos.y*cell_size,cell_size,cell_size)
        screen.blit(fruit_img,fruit_rect)
        
class SNAKE:
    def __init__(self):
        self.body = [Vector2(5,10),Vector2(4,10),Vector2(3,10)]
        self.direction = Vector2(0,0)
        self.add_block = False

        self.head_up = pygame.image.load('Graphics/head_up.png').convert_alpha()
        self.head_down = pygame.image.load('Graphics/head_down.png').convert_alpha()
        self.head_right = pygame.image.load('Graphics/head_right.png').convert_alpha()
        self.head_left = pygame.image.load('Graphics/head_left.png').convert_alpha()
            
        self.tail_up = pygame.image.load('Graphics/tail_up.png').convert_alpha()
        self.tail_down = pygame.image.load('Graphics/tail_down.png').convert_alpha()
        self.tail_right = pygame.image.load('Graphics/tail_right.png').convert_alpha()
        self.tail_left = pygame.image.load('Graphics/tail_left.png').convert_alpha()

        self.body_vertical = pygame.image.load('Graphics/body_vertical.png').convert_alpha()
        self.body_horizontal = pygame.image.load('Graphics/body_horizontal.png').convert_alpha()

        self.body_tr = pygame.image.load('Graphics/body_tr.png').convert_alpha()
        self.body_tl = pygame.image.load('Graphics/body_tl.png').convert_alpha()
        self.body_br = pygame.image.load('Graphics/body_br.png').convert_alpha()
        self.body_bl = pygame.image.load('Graphics/body_bl.png').convert_alpha()

    def draw_snake(self):
        self.update_head()
        self.update_tail()
        for index,block in enumerate(self.body):
            snake_rect = pygame.Rect(block.x*cell_size,block.y*cell_size,cell_size,cell_size)
            #Phần đầu
            if index == 0:
                screen.blit(self.head,snake_rect)
            #Phần đuôi
            elif index == len(self.body) - 1:
                screen.blit(self.tail,snake_rect)
            #Phần thân
            else:
                previous_block = self.body[index + 1] - block
                next_block = self.body[index - 1] - block
                #Nếu hoành độ của khối trước và sau của khối hiện tại trùng nhau thì rắn đang nằm ngang
                if previous_block.x == next_block.x:
                    screen.blit(self.body_vertical,snake_rect)
                #Nếu tung độ của khối trước và sau của khối hiện tại trùng nhau thì rắn đang nằm dọc
                elif previous_block.y == next_block.y:
                    screen.blit(self.body_horizontal,snake_rect)
                #Kiểm tra góc xoay khi rắn đổi hướng
                else:
                    if previous_block.x == -1 and next_block.y == -1 or previous_block.y == -1 and next_block.x == -1: screen.blit(self.body_tl,snake_rect)
                    elif previous_block.x == -1 and next_block.y == 1 or previous_block.y == 1 and next_block.x == -1: screen.blit(self.body_bl,snake_rect)
                    elif previous_block.x == 1 and next_block.y == -1 or previous_block.y == -1 and next_block.x == 1: screen.blit(self.body_tr,snake_rect)
                    elif previous_block.x == 1 and next_block.y == 1 or previous_block.y == 1 and next_block.x == 1: screen.blit(self.body_br,snake_rect)

    #Cập nhật đầu rắn dựa trên vị trí đầu và kế cận      
    def update_head(self):
        head_relation = self.body[0] - self.body[1]
        if head_relation == Vector2(-1,0): self.head = self.head_left
        elif head_relation == Vector2(1,0): self.head = self.head_right
        elif head_relation == Vector2(0,-1): self.head = self.head_up
        elif head_relation == Vector2(0,1): self.head = self.head_down

    #Cập nhật đuôi rắn dựa trên vị trí đuôi và kế cận 
    def update_tail(self):
        tail_relation = self.body[-2] - self.body[-1]
        if tail_relation == Vector2(-1,0): self.tail = self.tail_right
        elif tail_relation == Vector2(1,0): self.tail = self.tail_left
        elif tail_relation == Vector2(0,-1): self.tail = self.tail_down
        elif tail_relation == Vector2(0,1): self.tail = self.tail_up

    def move_snake(self):
        if self.add_block:
            body_copy = self.body[:] #Khi rắn ăn táo sẽ cập nhật vị trí cuối đã đi qua thành thân rắn
            self.add_block = False
        else: body_copy = self.body[:-1] #Loại bỏ vị trí cuối khi mà con rắn di chuyển tạo cảm giác con rắn đang di chuyển

        #Dịch chuyển phần đầu theo hướng di chuyển
        body_copy.insert(0,body_copy[0]+self.direction)
        #Dịch chuyển thân rắn theo vị trí đã dịch chuyển
        self.body = body_copy[:]

class MAIN:
    def __init__(self):
        self.snake = SNAKE()
        self.fruit = FRUIT()

    def draw_elements(self):
        self.snake.draw_snake()
        self.fruit.draw_fruit()
        self.draw_score()

    def check_collision(self):
        #Nếu đầu rắn chạm táo
        if self.snake.body[0] == self.fruit.pos:
            self.snake.add_block = True
            self.fruit.spawn_fruit()

        #Nếu rắn chạm tường
        if not 0 <= self.snake.body[0].x < cell_number or not 0 <= self.snake.body[0].y < cell_number:
            self.reset_game()

        #Nếu một trong phân thân rắn chạm đầu
        for block in self.snake.body[1:]:
            if block == self.snake.body[0]:
                self.reset_game()

    def draw_score(self):
        #Cập nhật điểm dựa trên chiều dài rắn
        score = str(len(self.snake.body)-3)
        pygame.display.set_caption("Score: {}".format(score))

    def reset_game(self):
        self.snake.body = [Vector2(5,10),Vector2(4,10),Vector2(3,10)]
        self.snake.direction = Vector2(0,0)
        self.fruit.pos = Vector2(15,10)

    def update(self):
        self.snake.move_snake()
        self.check_collision()

pygame.init()
#Tạo cảm giác đang vẽ grid
cell_size = 30
cell_number = 20
screen = pygame.display.set_mode((cell_size*cell_number,cell_size*cell_number))

#Timer
SCREEN_UPDATE = pygame.USEREVENT
pygame.time.set_timer(SCREEN_UPDATE,150)

main_game = MAIN()
running = True

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        if event.type == SCREEN_UPDATE:
            main_game.update()
            #Không cho đổi hướng liên tục
            limit = True
            
        if event.type == pygame.KEYDOWN and limit:
            if event.key == pygame.K_UP:
                #Không cho chuyển hướng ngược lại
                if main_game.snake.direction.y != 1:
                    main_game.snake.direction = Vector2(0,-1)
                    limit = False
            if event.key == pygame.K_DOWN:
                if main_game.snake.direction.y != -1:
                    main_game.snake.direction = Vector2(0,1)
                    limit = False
            if event.key == pygame.K_RIGHT:
                if main_game.snake.direction.x != -1:
                    main_game.snake.direction = Vector2(1,0)
                    limit = False
            if event.key == pygame.K_LEFT:
                if main_game.snake.direction.x != 1:
                    main_game.snake.direction = Vector2(-1,0)
                    limit = False
    
    screen.fill((175,215,70))
    main_game.draw_elements()

    pygame.display.update()
    pygame.time.Clock().tick(60)

pygame.quit()