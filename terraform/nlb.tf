### resources

resource "aws_lb" "this" {
  name               = "${local.unit_alias}-${var.environment}-${var.app_name}-nlb-int"
  load_balancer_type = "network"
  internal           = true
  subnets            = ["${module.vpc.private_subnet_ids}"]
  idle_timeout       = 60

  tags = {
    Name        = "${local.unit_alias}-${var.environment}-${var.app_name}-nlb-int"
    Environment = "${var.environment}"
    Service     = "${var.app_name}"
  }
}

resource "aws_lb_target_group" "this" {
  name        = "${var.app_name}-${var.environment}-int"
  target_type = "instance"

  port                 = 80
  protocol             = "TCP"
  vpc_id               = "${module.vpc.vpc_id}"
  deregistration_delay = 60

  health_check {
    protocol            = "TCP"
    port                = "traffic-port"
    interval            = 30
    healthy_threshold   = 3
    unhealthy_threshold = 3
  }
}

resource "aws_lb_listener" "this" {
  load_balancer_arn = "${aws_lb.this.arn}"
  port              = "80"
  protocol          = "TCP"

  default_action {
    target_group_arn = "${aws_lb_target_group.this.arn}"
    type             = "forward"
  }
}
