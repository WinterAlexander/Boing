var canvas;
var ctx;

var bodies;

$(function() {
    canvas = document.getElementById("testEngine");
    ctx = canvas.getContext("2d");

    init();
    setInterval(update, 16);


});

function init() {
    bodies = [{
        x: 50,
        y: 50,
        velX: 10,
        velY: 0,
        edges: [
            {
                x: 10,
                y: 0,
                length: 20,
                normalX: 1,
                normalY: 0
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
                length: 20,
                normalX: -1,
                normalY: 0
            }
        ],
        weight: 2
    }];
}

function update() {
    tick(1 / 60);
    render();
}

function tick(delta) {
    bodies = bodies.sort(function (a, b) {
        return a.weight - b.weight;
    })

    bodies.forEach(function(body) {
        move(body, body.velX * delta, body.velY * delta)
    })
}

function move(bodyA, x, y) {
    bodies.forEach(function(bodyB) {
        if(bodyA.weight > bodyB.weight) {

        } else {

        }
    })
}

function collision(bodyA, x, y, bodyB) {
    bodyA.edges.forEach(function(edgeA) {
        bodyB.edges.forEach(function(edgeB) {
            if(edgeA.normalX != -edgeB.normalX || edgeA.normalY != -edgeB.normalY)
                return

            if((bodyA.x + edgeA.x) * edgeA.normalX > (bodyB.x + edgeB.x) * edgeA.normalX
            || (bodyA.y + edgeA.y) * edgeA.normalY > (bodyB.y + edgeB.y) * edgeA.normalY)
                return


            if((bodyA.x + edgeA.x + x) * edgeA.normalX < (bodyB.x + edgeB.x) * edgeA.normalX
            || (bodyA.y + edgeA.y + y) * edgeA.normalY < (bodyB.y + edgeB.y) * edgeA.normalY)
                return

        })
    })
}

function render() {
	ctx.clearRect(0, 0, canvas.width, canvas.height);

    bodies.forEach(function(body) {
        ctx.beginPath();
        ctx.rect(body.x - 2, body.y - 2, 4, 4);
        ctx.fillStyle = "#50FF50";
        ctx.fill();
        ctx.closePath();

        body.edges.forEach(function(edge){
            ctx.beginPath();
            ctx.rect(body.x + edge.x - edge.length * Math.abs(edge.normalY) / 2,
                body.y + edge.y - edge.length * Math.abs(edge.normalX) / 2,
                Math.max(2, edge.length * Math.abs(edge.normalY)),
                Math.max(2, edge.length * Math.abs(edge.normalX)));

            ctx.fillStyle = "#FF5050";
            ctx.fill();
            ctx.closePath();
        })
    })
}