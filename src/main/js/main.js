let canvas;
let ctx;

let bodies;

let seed = 34352;

$(function() {
    canvas = document.getElementById("testEngine");
    ctx = canvas.getContext("2d");

    initRandom(100);
    setInterval(update, 10);
});

function init() {
    bodies = [{
        x: 50,
        y: 70,
        velX: 10,
        velY: -0.05,
        edges: [
            {
                x: 10,
                y: 0,
                length: 20,
                normalX: 1,
                normalY: 0
            },
            {
                x: -20,
                y: 0,
                length: 20,
                normalX: -1,
                normalY: 0
            },
            {
                x: -5,
                y: 10,
                length: 30,
                normalX: 0,
                normalY: 1
            },
            {
                x: -5,
                y: -10,
                length: 30,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 1
    },  {
        x: 300,
        y: 50,
        velX: -8,
        velY: 0,
        edges: [
            {
                x: -6,
                y: 0,
                length: 30,
                normalX: -1,
                normalY: 0
            },
            {
                x: 12,
                y: 0,
                length: 30,
                normalX: 1,
                normalY: 0
            },
            {
                x: 3,
                y: 15,
                length: 18,
                normalX: 0,
                normalY: 1
            },
            {
                x: 3,
                y: -15,
                length: 18,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 2
    },  {
        x: 0,
        y: 40,
        velX: 2,
        velY: 0.5,
        edges: [
            {
                x: -6,
                y: 0,
                length: 30,
                normalX: -1,
                normalY: 0
            },
            {
                x: 12,
                y: 0,
                length: 30,
                normalX: 1,
                normalY: 0
            },
            {
                x: 3,
                y: 15,
                length: 18,
                normalX: 0,
                normalY: 1
            },
            {
                x: 3,
                y: -15,
                length: 18,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 3
    }];
}

function initRandom(count) {
    bodies = [];
    for(let i = 0; i < count; i++) {
        let body = {
            x: random() * 380 + 10,
            y: random() * 380 + 10,
            velX: (random() * 2 - 1) * 5,
            velY: (random() * 2 - 1) * 5,
            edges: [
                {
                    x: -10,
                    y: 0,
                    length: 20,
                    normalX: -1,
                    normalY: 0
                },
                {
                    x: 10,
                    y: 0,
                    length: 20,
                    normalX: 1,
                    normalY: 0
                },
                {
                    x: 0,
                    y: -10,
                    length: 20,
                    normalX: 0,
                    normalY: -1
                },
                {
                    x: 0,
                    y: 10,
                    length: 20,
                    normalX: 0,
                    normalY: 1
                }
            ],
            weight: i
        };

        addIfNotColliding: {
            for(let other of bodies)
                if(Math.max(Math.abs(other.x - body.x), Math.abs(other.y - body.y)) < 20)
                {
                    i--;
                    break addIfNotColliding;
                }


            bodies.push(body);
        }
    }
    makeBorder();
}

function initChain() {
    bodies = [];

    for(let i = 0; i < 2; i++) {
        bodies.push({
            x: i * 25 + 50,
            y: 200,
            velX: -10,
            velY: 0,
            edges: [
                {
                    x: -10,
                    y: 0,
                    length: 20,
                    normalX: -1,
                    normalY: 0
                },
                {
                    x: 10,
                    y: 0,
                    length: 20,
                    normalX: 1,
                    normalY: 0
                },
                {
                    x: 0,
                    y: -10,
                    length: 20,
                    normalX: 0,
                    normalY: -1
                },
                {
                    x: 0,
                    y: 10,
                    length: 20,
                    normalX: 0,
                    normalY: 1
                }
            ],
            weight: i
        });
    }

    makeBorder();
}

function initCornerCorner() {
    bodies = [{
        x: 100,
        y: 100,
        velX: 10,
        velY: 10,
        edges: [
            {
                x: 10,
                y: 0,
                length: 20,
                normalX: 1,
                normalY: 0
            },
            {
                x: -10,
                y: 0,
                length: 20,
                normalX: -1,
                normalY: 0
            },
            {
                x: 0,
                y: 10,
                length: 20,
                normalX: 0,
                normalY: 1
            },
            {
                x: 0,
                y: -10,
                length: 20,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 1
    },  {
        x: 300,
        y: 300,
        velX: -10,
        velY: -10,
        edges: [
            {
                x: 10,
                y: 0,
                length: 20,
                normalX: 1,
                normalY: 0
            },
            {
                x: -10,
                y: 0,
                length: 20,
                normalX: -1,
                normalY: 0
            },
            {
                x: 0,
                y: 10,
                length: 20,
                normalX: 0,
                normalY: 1
            },
            {
                x: 0,
                y: -10,
                length: 20,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 2
    }];
}

function initPlayerAndTiles() {
    bodies = [];

    for(let i = 0; i < 400; i += 20)
    {
        if(i == 200)
            continue;

        bodies.push({
           x: i,
           y: 300,
           velX: 0,
           velY: 0,
           edges: [
                {
                    x: 10,
                    y: 0,
                    length: 20,
                    normalX: 1,
                    normalY: 0
                },
                {
                    x: -10,
                    y: 0,
                    length: 20,
                    normalX: -1,
                    normalY: 0
                },
                {
                    x: 0,
                    y: 10,
                    length: 20,
                    normalX: 0,
                    normalY: 1
                },
                {
                    x: 0,
                    y: -10,
                    length: 20,
                    normalX: 0,
                    normalY: -1
                }
            ],
            weight: 1000 + i
        });
    }

    let player = {
      x: 200,
      y: 200,
      velX: 0,
      velY: 0,

        edges: [
            {
                x: 10,
                y: 0,
                length: 20,
                normalX: 1,
                normalY: 0
            },
            {
                x: -10,
                y: 0,
                length: 20,
                normalX: -1,
                normalY: 0
            },
            {
                x: 0,
                y: 10,
                length: 20,
                normalX: 0,
                normalY: 1
            },
            {
                x: 0,
                y: -10,
                length: 20,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 1
    };

    document.addEventListener("keydown", function(e) {
        if (e.keyCode == 39 || e.keyCode == 68) {
            player.velX = 10;
            e.preventDefault();
        } else if (e.keyCode == 38 || e.keyCode == 87 || e.keyCode == 32) {
            player.velY = -10;
            e.preventDefault();
        } else if (e.keyCode == 37 || e.keyCode == 65) {
            player.velX = -10;
            e.preventDefault();
        } else if (e.keyCode == 40) {
            player.velY = 10;
            e.preventDefault();
        }
    }, false);
    document.addEventListener("keyup", function(e) {
        if(e.keyCode == 39 || e.keyCode == 68) {
            player.velX = 0;
            e.preventDefault();
        } else if(e.keyCode == 38 || e.keyCode == 87 || e.keyCode == 32) {
            player.velY = 0;
            e.preventDefault();
        } else if(e.keyCode == 37 || e.keyCode == 65) {
            player.velX = 0;
            e.preventDefault();
        } else if(e.keyCode == 40) {
            player.velY = 0;
            e.preventDefault();
        }
    }, false);

    bodies.push(player);
}

function makeBorder() {
    bodies.push({
        x: 200,
        y: 200,
        velX: 0,
        velY: 0,
        edges: [
            {
                x: -200,
                y: 0,
                length: 400,
                normalX: 1,
                normalY: 0
            },
            {
                x: 200,
                y: 0,
                length: 400,
                normalX: -1,
                normalY: 0
            },
            {
                x: 0,
                y: -200,
                length: 400,
                normalX: 0,
                normalY: 1
            },
            {
                x: 0,
                y: 200,
                length: 400,
                normalX: 0,
                normalY: -1
            }
        ],
        weight: 1000
    });
}

function update() {
    tick(1 / 50);
    render();
}

function tick(delta) {
    bodies = bodies.sort((a, b) => {
        return b.weight - a.weight;
    });

    for(let body of bodies) {
        move(body, body.velX * delta, body.velY * delta, body.weight);
    }
}

function move(bodyA, x, y, weight) {
    for(let bodyB of bodies) {
        if(bodyA == bodyB)
            continue;

        if(weight > bodyB.weight) {
            let moved = true;
            collision(bodyA, x, y, bodyB).forEach(coll => {
                if(!move(bodyB, coll.penetration * coll.normalX, coll.penetration * coll.normalY, weight))
                    moved = false;
            });

            if(moved)
                continue;
        }

        let collided = false;

        let pX = 0, pY = 0;

        collision(bodyA, x, y, bodyB).forEach(coll => {
            collided = true;
            pX -= coll.penetration * coll.normalX;
            pY -= coll.penetration * coll.normalY;
        });

        if(!collided || !(Math.abs(pX) + Math.abs(pY)))
            continue;

        if(weight == bodyB.weight) {
            console.log("warning, collided with pusher (" + x + "," + y + ")");
        }

        move(bodyA, pX, pY, bodyB.weight);

        if(Math.abs(x + pX) + Math.abs(y + pY))
            move(bodyA, x + pX, y + pY, weight);
        return false;
    }

    bodyA.x += x;
    bodyA.y += y;
    return true;
}

function collision(bodyA, x, y, bodyB) {
    let collisions = [];

    for(let edgeA of bodyA.edges) {
        for(let edgeB of bodyB.edges) {
            if(edgeA.normalX != -edgeB.normalX || edgeA.normalY != -edgeB.normalY)
                continue;

            let rAx = bodyA.x + edgeA.x;
            let rAy = bodyA.y + edgeA.y;
            let rBx = bodyB.x + edgeB.x;
            let rBy = bodyB.y + edgeB.y;

            if(rAx * edgeA.normalX > rBx * edgeA.normalX
            || rAy * edgeA.normalY > rBy * edgeA.normalY)
                continue;

            let pene = dot(rAx - rBx + x, rAy - rBy + y, edgeA.normalX, edgeA.normalY);

            if(pene <= 0)
                continue;

            let t = dot(rAx - rBx, rAy - rBy, edgeA.normalX, edgeA.normalY) /
                dot(-x, -y, edgeA.normalX, edgeA.normalY);

            let cAx = rAx + t * x;
            let cAy = rAy + t * y;

            let surface = contactSurface(cAx, cAy, edgeA.length, rBx, rBy, edgeB.length, edgeA.normalX, edgeA.normalY);

            if(surface < 0 || !surface && ((x < y) ^ Math.abs(edgeA.normalX)))
                continue;

            collisions.push({
                penetration: pene,
                normalX: edgeA.normalX,
                normalY: edgeA.normalY
            });
        }
    }

    return collisions;
}

function render() {
	ctx.clearRect(0, 0, canvas.width, canvas.height);

    for(let body of bodies) {
        let color = randColor(body.weight);

        ctx.beginPath();
        ctx.rect(body.x - 2, body.y - 2, 4, 4);
        ctx.fillStyle = color;
        ctx.fill();
        ctx.closePath();

        for(let edge of body.edges) {
            ctx.beginPath();
            ctx.rect(body.x + edge.x - edge.length * Math.abs(edge.normalY) / 2,
                body.y + edge.y - edge.length * Math.abs(edge.normalX) / 2,
                Math.max(2, edge.length * Math.abs(edge.normalY)),
                Math.max(2, edge.length * Math.abs(edge.normalX)));

            ctx.fillStyle = color;
            ctx.fill();
            ctx.closePath();
        }
    }
}

function contactSurface(ax, ay, sizeA, bx, by, sizeB, nx, ny) {
    sizeA /= 2;
    sizeB /= 2;

    //we take the 2 extremities of the 2 limits
    let limitA1 = ny * (ax + sizeA) + nx * (ay + sizeA);
    let limitA2 = ny * (ax - sizeA) + nx * (ay - sizeA);
    let limitB1 = ny * (bx + sizeB) + nx * (by + sizeB);
    let limitB2 = ny * (bx - sizeB) + nx * (by - sizeB);

    //yields the overlapping length
    return Math.min(Math.max(limitA1, limitA2), Math.max(limitB1, limitB2)) //minimum of the maximums
        - Math.max(Math.min(limitA1, limitA2), Math.min(limitB1, limitB2)); //maximum of the minimums
}

function dot(x1, y1, x2, y2) {
    return x1 * x2 + y1 * y2;
}

function pow2(x) {
    return x * x;
}

function random() {
    return rand(seed++);
}

function rand(seed) {
    let x = Math.sin(seed) * 10000;
    return x - Math.floor(x);
}

function randColor(seed) {
    return "rgb(" + (rand(seed) * 240) + ","
        + (rand(seed * 8) * 240) + ","
        + (rand(seed - 15) * 240) + ")";
}